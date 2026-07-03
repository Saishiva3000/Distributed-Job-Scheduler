package com.shiva.jobscheduler.controller;

import com.shiva.jobscheduler.dto.CreateJobDto;
import com.shiva.jobscheduler.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createJob(@Valid @RequestBody CreateJobDto jobDto){
        return ResponseEntity.ok(jobService.createJob(jobDto));
    }

    @GetMapping
    public ResponseEntity<?> getJob(@RequestParam UUID jobId){
        return  ResponseEntity.status(HttpStatus.FOUND).body(jobService.getJob(jobId));
    }
}
