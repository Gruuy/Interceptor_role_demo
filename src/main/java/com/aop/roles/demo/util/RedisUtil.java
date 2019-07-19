package com.aop.roles.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 10:19 2019/7/19
 */
public class RedisUtil {
    public static final String TOKEN_HEAD="Token&";
}
