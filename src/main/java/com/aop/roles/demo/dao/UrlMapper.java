package com.aop.roles.demo.dao;


import com.aop.roles.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Url数据访问层
 * @author Gruuy
 * @date 2019-7-18
 */

@Mapper
@Repository("urlMapper")
public interface UrlMapper {
    List<Role> ListAllRole();
    List<Role> ListRoleFromUrl(@Param("url") String url);
}
