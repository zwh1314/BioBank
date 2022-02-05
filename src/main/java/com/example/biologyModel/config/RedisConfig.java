package com.example.biologyModel.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    throws UnknownHostException {
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //配置JSON序列化
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //转义
        ObjectMapper om=new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectJackson2JsonRedisSerializer.setObjectMapper(om);
        //配置String序列化
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();

        //redis的key采用String序列化的方式
        template.setKeySerializer(stringRedisSerializer);
        //redis的hash的key也采用String序列化的方式
        template.setHashKeySerializer(stringRedisSerializer);
        //redis的value采用Jackson（json）序列化的方式
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        //redis的hash的value也采用Jackson（json）序列化的方式
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
