package com.jobscheduler.scheduler.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class JobHash {

    @Id
    private UUID jobId;

    private String jobName;
    private String status;
    private LocalDateTime scheduledAt;
}
