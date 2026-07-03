package com.shiva.jobscheduler.controller;

import com.shiva.jobscheduler.model.Job;
import com.shiva.jobscheduler.service.JobService;
import com.shiva.jobscheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/schedule")
    public void schedule(){
        schedulerService.schedule();
    }
}
