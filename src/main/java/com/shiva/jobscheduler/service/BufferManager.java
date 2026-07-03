package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Job;

import java.util.PriorityQueue;

public interface BufferManager {

    public PriorityQueue<Job> loadJobs();
}
