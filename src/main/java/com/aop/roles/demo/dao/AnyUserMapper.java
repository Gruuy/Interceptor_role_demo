package com.aop.roles.demo.dao;

import com.aop.roles.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 获取用户信息dao（UserDetails）
 * @author Gruuy
 * @date 2019-7-16
 */

@Repository("anyUserMapper")
@Mapper
public interface AnyUserMapper {

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByUsername(@Param("username") String username);
    /**
     * 通过用户ID获取用户信息
     * @param userId
     * @return
     */
    User getUserByUserId(@Param("userId") Integer userId);
}
