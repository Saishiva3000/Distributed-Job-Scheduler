package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Job;
import com.shiva.jobscheduler.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service
public class BufferManagerImpl implements BufferManager{

    private final JobRepository jobRepository;

    @Autowired
    public BufferManagerImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public PriorityQueue<Job> loadJobs() {
        return new PriorityQueue<>(jobRepository.getJobsByTime());
    }
}
