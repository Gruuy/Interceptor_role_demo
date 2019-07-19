package com.aop.roles.demo.service;

import com.aop.roles.demo.entity.Role;

import java.util.List;

/**
 * 获取需要授权的链接的接口
 * @author Gruuy
 * @date 2019-7-18
 */
public interface UrlService {

    /**
     * 获取所有需要授权的链接
     */
    List<Role> ListAllMenu();

    /**
     * 获取当前链接需要的授权信息
     */
    List<Role> ListRoleFromUrl(String url);
}
