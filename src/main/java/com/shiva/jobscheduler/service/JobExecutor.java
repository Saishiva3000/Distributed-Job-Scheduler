package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Job;
import org.springframework.stereotype.Service;

@Service
public class JobExecutor {

    private final JobService jobService;

    public JobExecutor(JobService jobService) {
        this.jobService = jobService;
    }

    public void execute(Job job){
        jobService.updateStatus(job.getId());
        System.out.println("executing" + job.getJobName());
    }
}
