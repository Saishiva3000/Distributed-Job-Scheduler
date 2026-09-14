package com.jobscheduler.scheduler.service;

import com.djs.common.events.JobEvent;
import com.djs.common.events.JobEventType;
import com.jobscheduler.scheduler.model.Buffer;
import com.jobscheduler.scheduler.model.JobHash;
import com.jobscheduler.scheduler.repository.JobHashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Buffer buffer;
    private final BufferManager bufferManager;
    private long wait;
    private final TaskExecutor taskExecutor;
    private final RoutingKafkaTemplate kafkaTemplate;
    private final JobHashRepository jobHashRepository;


    public void schedule() {
        Runnable task = ()->{
            while (true){
                while (!buffer.getQueue().isEmpty()){
                    synchronized (bufferManager.getBufferLock()){
                        JobHash hash =  bufferManager.getJobWithNextExecutionTime();
                        wait = Duration.between(LocalDateTime.now(),hash.getScheduledAt()).toMillis();
                        if(wait>0){
                            try {
                                bufferManager.getBufferLock().wait(wait);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        JobEvent event = new JobEvent(
                                hash.getJobId(),
                                hash.getJobName(),
                                JobEventType.SCHEDULED,
                                hash.getScheduledAt(),
                                hash.getStatus(),
                                LocalDateTime.now()
                        );
                        ProducerRecord<Object, Object> record = new ProducerRecord<>(
                                "JOB_SCHEDULED",
                                hash.getJobId(),
                                event
                        );
                        CompletableFuture<SendResult<Object,Object>> future = kafkaTemplate.send(record);
                        future.whenComplete( (result,e)->{
                            if(e!=null){
                                jobHashRepository.delete(hash);
                                return;
                            }
                            log.info("kafka event failed");
                        });
                    }
                }
                try {
                    bufferManager.getBufferLock().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        taskExecutor.execute(task);
    }
}
