package com.jobscheduler.loaderservice.messaging;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
public class ProducerConfig {

    @Bean
    public ProducerFactory<Object,Object> producerFactory(){
        Map<String,Object> properties = new HashMap<>();
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG,20);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG,3);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG,3);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG,"all");

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public RoutingKafkaTemplate routingKafkaTemplate(GenericApplicationContext context,
                                                     ProducerFactory<Object,Object> producerFactory){
        Map<String,Object> config = producerFactory.getConfigurationProperties();

        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JacksonSerializableSerializer.class);
        DefaultKafkaProducerFactory<Object,Object> jacksonPF = new DefaultKafkaProducerFactory<>(config);
        context.registerBean("jacksonPF",DefaultKafkaProducerFactory.class,()->jacksonPF);

        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,);
        DefaultKafkaProducerFactory<Object,Object> createdExpiredPF = new DefaultKafkaProducerFactory<>(config);
        context.registerBean("createdExpiredPF", DefaultKafkaProducerFactory.class,()->createdExpiredPF);

        Map<Pattern,ProducerFactory<Object,Object>> map = new LinkedHashMap<>();
        map.put(Pattern.compile("yoo"),jacksonPF);
        map.put(Pattern.compile("URL_*"),createdExpiredPF);
        return new RoutingKafkaTemplate(map);
    }
}
