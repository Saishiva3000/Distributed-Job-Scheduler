package com.shiva.jobscheduler.repository;

import com.shiva.jobscheduler.model.Job;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CJobRepository {

    public List<Job> getJobsByTime();
}
