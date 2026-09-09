package com.jobscheduler.loaderservice.repository;

import com.jobscheduler.loaderservice.model.JobHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadedJobRepository extends CrudRepository<JobHash,Long> {
}
