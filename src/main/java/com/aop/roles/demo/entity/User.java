package com.aop.roles.demo.entity;


import java.util.List;

/**
 * 相当于Security的UserDetails
 * 用户详情实体
 * @author Gruuy
 * @date 2019-7-16
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private List<String> role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
