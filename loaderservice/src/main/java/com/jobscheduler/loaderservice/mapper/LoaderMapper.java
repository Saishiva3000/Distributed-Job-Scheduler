package com.jobscheduler.loaderservice.mapper;

import com.djs.common.events.JobEvent;
import com.jobscheduler.loaderservice.model.JobHash;
import org.springframework.stereotype.Component;

@Component
public class LoaderMapper {

    public JobHash map(JobEvent event){
        return new JobHash(
                event.getId(),
                event.getJobName(),
                event.getStatus(),
                event.getScheduledAt()
        );
    }
}
