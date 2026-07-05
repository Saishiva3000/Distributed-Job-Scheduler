package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Buffer;
import com.shiva.jobscheduler.model.Job;
import com.shiva.jobscheduler.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class SchedulerService {

    private final JobRepository jobRepository;
    private final JobExecutor jobExecutor;
    private final Buffer buffer;
    private final BufferManager bufferManager;
    private Thread schedulerThread;
    private long wait;

    public SchedulerService(JobRepository jobRepository , JobExecutor jobExecutor, Buffer buffer, BufferManager bufferManager) {
        this.jobRepository = jobRepository;
        this.jobExecutor = jobExecutor;
        this.buffer = buffer;
        this.bufferManager = bufferManager;
    }

    public void schedule(){
         schedulerThread = new Thread(()->{
             while(true){
                 Job job = new Job();
                 synchronized (bufferManager.getBufferLock()){
                     while(!buffer.getQueue().isEmpty()){
                         job = bufferManager.getJobWithNextExecutionTime();
                         wait = Duration.between(LocalDateTime.now(),job.getScheduledAt())
                                 .toMillis();
                         if(wait > 0){
                             try {
                                 bufferManager.getBufferLock().wait(wait);
                                 continue;
                             } catch (InterruptedException e) {
                                 log.error("Thread started {}",e);
                             }
                         }
                         job =  buffer.getQueue().poll();
                     }
                     try {
                         bufferManager.getBufferLock().wait();
                     } catch (InterruptedException e) {
                         log.error("Thread interupted {}",e);
                     }
                     jobExecutor.execute(job);
                 }
             }
        });
        schedulerThread.start();
    }
}
