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
    private final BufferManager bufferManager;
    private final SchedulerService schedulerService;

    @Autowired
    public JobService(JobRepository jobRepository, ModelMapper mapper, BufferManager bufferManager, SchedulerService schedulerService) {
        this.jobRepository = jobRepository;
        this.mapper = mapper;
        this.bufferManager = bufferManager;
        this.schedulerService = schedulerService;
    }

    @Transactional
    public ResponseDto createJob(CreateJobDto jobDto){
        Job job =mapper.map(jobDto,Job.class);
        System.out.println(job.getJobName());
        job.setStatus(Job.JobStatus.PENDING);
        job.setDeleted(false);
        job = jobRepository.save(job);
        synchronized (bufferManager.getBufferLock()){
            bufferManager.addJobs(job);
            bufferManager.getBufferLock().notifyAll();
        }
        return mapper.map(job, ResponseDto.class);
    }

    public ResponseDto getJob(UUID id){
        Job job = jobRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new JobNotFoundException("could not find"));
        return mapper.map(job,ResponseDto.class);
    }

    @Transactional
    public void delete(UUID id){
        Job job = jobRepository.findById(id)
                .orElseThrow(()-> new JobNotFoundException("could not find"));
        job.setDeleted(true);
        job.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void updateStatus(UUID jobId, Job.JobStatus status){
        Job job = jobRepository.findByIdAndIsDeletedFalse(jobId)
                .orElseThrow(()-> new JobNotFoundException("not FOUND"));
        job.setStatus(status);
    }
}
