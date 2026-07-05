package com.shiva.jobscheduler.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Buffer {

    private PriorityQueue<Job> queue = new PriorityQueue<>();

}
