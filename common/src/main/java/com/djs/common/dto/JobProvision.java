package com.djs.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobProvision implements Comparable<JobProvision>{
    private UUID jobId;
    private String jobName;
    private LocalDateTime scheduledAt;
    private String status;

    @Override
    public int compareTo(JobProvision o) {
        return this.scheduledAt.compareTo(o.scheduledAt);
    }
}
