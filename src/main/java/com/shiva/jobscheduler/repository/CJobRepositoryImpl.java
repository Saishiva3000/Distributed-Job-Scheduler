package com.shiva.jobscheduler.repository;

import com.shiva.jobscheduler.model.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CJobRepositoryImpl implements CJobRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Job> getJobsByTime() {
        TypedQuery<Job> query = entityManager.createQuery(
                "select j from Job j where j.scheduledAt<= CURRENT_TIMESTAMP and j.status = 'PENDING' and j.isDeleted = FALSE ",
                Job.class);
        query.setMaxResults(100);
        return query.getResultList();
    }
}
