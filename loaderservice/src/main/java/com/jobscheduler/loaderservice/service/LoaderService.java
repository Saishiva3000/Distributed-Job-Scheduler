package com.jobscheduler.loaderservice.service;

import com.djs.common.events.JobEvent;
import com.djs.common.events.JobEventType;
import com.djs.common.exception.ScheduledJobModificationException;
import com.jobscheduler.loaderservice.mapper.LoaderMapper;
import com.jobscheduler.loaderservice.model.JobHash;
import com.jobscheduler.loaderservice.repository.LoadedJobRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoaderService {

    private final LoadedJobRepository loadedJobRepository;
    private final LoaderMapper loaderMapper;

    @KafkaListener(topics = {"JOB_CREATED_EVENT"})
    public void addJob(ConsumerRecord<UUID, JobEvent> record){
        JobEvent event = record.value();
        LocalDateTime scheduledAt = event.getScheduledAt();
        if(scheduledAt.isAfter(LocalDateTime.now())){
            return;
        }
        JobHash hash = loaderMapper.map(event);
        loadedJobRepository.save(hash);
    }

    @KafkaListener(topics = {"JOB_CANCELLED_EVENT"})
    public void deleteJob(ConsumerRecord<UUID,JobEvent> record){
        JobEvent event = record.value();
        LocalDateTime scheduledAt = event.getScheduledAt();
        if(scheduledAt.isBefore(LocalDateTime.now().plusMinutes(1))){
            throw new ScheduledJobModificationException("the job is already scheduled try force delete before 1min");
        }

        JobHash hash = loaderMapper.map(event);
        loadedJobRepository.delete(hash);
    }

    @KafkaListener(topics = {"JOB_LOADING_EVENT"})
    public void loadjobs(ConsumerRecord<UUID,JobEvent> record){
        JobEvent event = record.value();
        JobHash hash = loaderMapper.map(event);
        loadedJobRepository.save(hash);
    }
}
