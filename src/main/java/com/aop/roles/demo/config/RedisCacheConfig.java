package com.aop.roles.demo.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 10:12 2019/7/19
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //  RedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        //配置序列化方式为字符串
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer =new StringRedisSerializer();
        // 连接redisFactory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置各种数据类型的序列化方式为String
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        //初始化Bean
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
