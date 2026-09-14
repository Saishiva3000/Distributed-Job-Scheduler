package com.jobscheduler.scheduler.repository;

import com.jobscheduler.scheduler.model.JobHash;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobHashRepository extends CrudRepository<JobHash, UUID> {

    List<JobHash> findTop100ByStatusAndScheduledAtLessThanEqual(
            String status,
            LocalDateTime time
    );
}
