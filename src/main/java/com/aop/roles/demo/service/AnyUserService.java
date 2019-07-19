package com.aop.roles.demo.service;

import com.aop.roles.demo.entity.User;

import java.util.List;


/**
 *  获取用户权限信息接口
 * @author Gruuy
 * @date 2019-7-16
 */
public interface AnyUserService {

    /**
     *  根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByName(String username);


    /**
     * 校验用户登陆信息
     * @param user
     * @return
     */
    Integer login(User user);

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    User getUserByPrimaryKey(Integer userId);
}
