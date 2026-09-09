package com.djs.common.events.serializers;

import org.apache.kafka.common.serialization.Serializer;

public class JobEventSerializer implements Serializer<JobEventSerializer> {

    @Override
    public byte[] serialize(String s, JobEventSerializer jobEventSerializer) {
        return new byte[0];
    }
}
