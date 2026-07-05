package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.model.Buffer;
import com.shiva.jobscheduler.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service
public class BufferManager {

    private final Buffer buffer;
    private Object bufferLock = new Object();

    @Autowired
    public BufferManager(Buffer buffer) {
        this.buffer = buffer;
    }

    public void addJobs(Job job){
        buffer.getQueue().offer(job);
    }

    public Job getJobWithNextExecutionTime(){
        return buffer.getQueue().peek();
    }

    public Object getBufferLock() {
        return bufferLock;
    }
}
