package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Job;
import com.shiva.jobscheduler.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service
public class SchedulerService {

    private final JobRepository jobRepository;
    private final BufferManager bufferManager;
    private final JobExecutor jobExecutor;
    private PriorityQueue<Job> queue;

    public SchedulerService(JobRepository jobRepository, BufferManager bufferManager, JobExecutor jobExecutor) {
        this.jobRepository = jobRepository;
        this.bufferManager = bufferManager;
        this.jobExecutor = jobExecutor;
    }

    public void schedule(){
        Thread t1 = new Thread(()->{
            while(true){
                queue = bufferManager.loadJobs();
                while(!queue.isEmpty()){
                    jobExecutor.execute(queue.poll());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
    }
}
