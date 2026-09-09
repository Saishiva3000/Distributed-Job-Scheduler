package com.jobscheduler.loaderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
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
        RedisTemplate<byte[],byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<byte[]> byteArraySerializer = RedisSerializer.byteArray();
        redisTemplate.setKeySerializer(byteArraySerializer);
        redisTemplate.setValueSerializer(byteArraySerializer);
        redisTemplate.setHashKeySerializer(byteArraySerializer);
        redisTemplate.setHashValueSerializer(byteArraySerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
