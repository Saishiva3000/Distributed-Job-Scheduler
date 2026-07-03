package com.shiva.jobscheduler.dto;

import com.shiva.jobscheduler.model.Job;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDto {

    @NotNull
    @NotBlank
    private String jobName;

    @NotNull
    @Future
    private LocalDateTime scheduledAt;

    @NotNull
    private Job.JobStatus status;
}
