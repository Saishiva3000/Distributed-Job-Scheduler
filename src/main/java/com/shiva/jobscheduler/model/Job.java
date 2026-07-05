package com.shiva.jobscheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(
        indexes = {
                @Index(name = "idx_time_status_deleted" ,
                        columnList = "scheduledAt,status,isDeleted")
        }
)
public class Job implements Comparable<Job>{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String jobName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private boolean isDeleted;

    @JsonIgnore
    private LocalDateTime deletedAt;

    public Job(String jobName,LocalDateTime scheduledAt, JobStatus status) {
        this.jobName = jobName;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.isDeleted=false;
    }

    @Override
    public int compareTo(Job j) {
        return this.scheduledAt.compareTo(j.getScheduledAt());
    }

    public enum JobStatus {
        PENDING,
        RUNNING,
        COMPLETED,
        FAILED
    }

}
