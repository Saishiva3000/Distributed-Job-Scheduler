package com.jobscheduler.scheduler.service;

import com.jobscheduler.scheduler.model.Buffer;
import com.jobscheduler.scheduler.model.JobHash;
import com.jobscheduler.scheduler.repository.JobHashRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BufferManager {

    private final Buffer buffer;
    @Getter
    private Object bufferLock = new Object();
    private final JobHashRepository jobHashRepository;

    @Autowired
    public BufferManager(Buffer buffer, JobHashRepository jobHashRepository) {
        this.buffer = buffer;
        this.jobHashRepository = jobHashRepository;
    }

    public void addJob(JobHash job){
        buffer.getQueue().offer(job);
    }

    public JobHash getJobWithNextExecutionTime(){
        return buffer.getQueue().peek();
    }

    public void loadJobs(){
        List<JobHash> jobs = jobHashRepository.findTop100ByStatusAndScheduledAtLessThanEqual(
                "LOADED",
                LocalDateTime.now().plusSeconds(60)
        );
        buffer.getQueue().addAll(jobs);
    }
}
