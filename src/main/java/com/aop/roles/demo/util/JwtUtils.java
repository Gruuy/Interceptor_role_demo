package com.aop.roles.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Jwt工具类
 * @author Gruuy
 * @date 2019-7-17
 */
public class JwtUtils {

    /**
     * 过期时间 5 分钟
     */
    public static final long EXPIRE_TIME = 5 * 60 * 1000;
    /**
     * 密钥
     */
    public static final String PRI_KEY = "123456789";

    /**
     *  根据userId生成token
     * @param userId 登录名
     * @return
     */
    public static String generateToken(Integer userId){
        // 设置过期时间，30分钟
        Date date = new Date(System.currentTimeMillis() + JwtUtils.EXPIRE_TIME);
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, JwtUtils.PRI_KEY)
                .compact();
        return token;
    }

}
