package com.shiva.jobscheduler.service;

import com.shiva.jobscheduler.dto.CreateJobDto;
import com.shiva.jobscheduler.dto.ResponseDto;
import com.shiva.jobscheduler.exception.JobNotFoundException;
import com.shiva.jobscheduler.model.Job;
//import com.shiva.jobscheduler.modelmapper.JobMapper;
import com.shiva.jobscheduler.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ModelMapper mapper;

    @Autowired
    public JobService(JobRepository jobRepository, ModelMapper mapper) {
        this.jobRepository = jobRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ResponseDto createJob(CreateJobDto jobDto){
        Job job =mapper.map(jobDto,Job.class);
        System.out.println(job.getJobName());
        job.setStatus(Job.JobStatus.PENDING);
        job.setDeleted(false);
        return mapper.map(jobRepository.save(job), ResponseDto.class);
    }

    public ResponseDto getJob(UUID id){
        Job job = jobRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new JobNotFoundException("could not find"));
        return mapper.map(job,ResponseDto.class);
    }

    @Transactional
    public void delete(UUID id){
        Job job = jobRepository.getReferenceById(id);
        job.setDeleted(true);
        job.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void updateStatus(UUID jobId){
        Job job = jobRepository.getReferenceById(jobId);
        job.setStatus(Job.JobStatus.RUNNING);
    }
}
