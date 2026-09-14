package com.jobscheduler.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<byte[],byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<byte[],byte[]> template = new RedisTemplate<>();
        RedisSerializer<byte[]> byteArraySerializer = RedisSerializer.byteArray();
        template.setKeySerializer(byteArraySerializer);
        template.setHashValueSerializer(byteArraySerializer);
        template.setValueSerializer(byteArraySerializer);
        template.setHashKeySerializer(byteArraySerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}
