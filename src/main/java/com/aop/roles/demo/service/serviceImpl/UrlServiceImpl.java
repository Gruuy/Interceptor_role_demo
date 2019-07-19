package com.aop.roles.demo.service.serviceImpl;

import com.aop.roles.demo.dao.UrlMapper;
import com.aop.roles.demo.entity.Role;
import com.aop.roles.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 授权Url业务层
 * @author Gruuy
 * @date 2019-7-18
 */
@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlMapper urlMapper;

    /**
     * 获取所有需要验证的url
     */
    @Override
    public List<Role> ListAllMenu() {
        return urlMapper.ListAllRole();
    }

    /**
     * 获取当前url需要的角色
     */
    @Override
    public List<Role> ListRoleFromUrl(String url) {
        return urlMapper.ListRoleFromUrl(url);
    }
}
