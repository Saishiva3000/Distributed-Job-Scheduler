package com.djs.common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
public class JobEvent {

    private UUID id;
    private String jobName;
    private JobEventType type;
    private LocalDateTime scheduledAt;
    private String status;
    private LocalDateTime timestamp;
}
