package com.jobscheduler.scheduler.model;

import com.djs.common.dto.JobProvision;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Buffer {

    private PriorityQueue<JobProvision> queue = new PriorityQueue<>();

}
