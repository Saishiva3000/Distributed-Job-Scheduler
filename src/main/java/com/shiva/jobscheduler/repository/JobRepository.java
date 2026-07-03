package com.shiva.jobscheduler.repository;

import com.shiva.jobscheduler.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> ,CJobRepository{

    Optional<Job> findByIdAndIsDeletedFalse(UUID uuid);
}
