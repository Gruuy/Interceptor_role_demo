package com.aop.roles.demo.controller;

import com.aop.roles.demo.entity.User;
import com.aop.roles.demo.service.AnyUserService;
import com.aop.roles.demo.util.JwtUtils;
import com.aop.roles.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 * @author Gruuy
 * @date 2019-7-18
 */

@RestController
public class TestController {

    @Autowired
    private AnyUserService anyUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    public Map<String,Object> test(){
        Map<String,Object> map=new HashMap<>(1);
        map.put("msg","hello");
        return map;
    }

    @RequestMapping("/test2")
    public Map<String,Object> test2(){
        Map<String,Object> map=new HashMap<>(1);
        map.put("msg","test2");
        return map;
    }

    @PostMapping("/getToken")
    public Map<String,Object> getToken(@RequestBody User user){
        Map<String,Object> map=new HashMap<>();
        if(user==null){
            map.put("code",20000);
            map.put("message","参数有误！");
            return map;
        }
        if(user.getUsername()==null||user.getUsername().equals("")){
            map.put("code",20000);
            map.put("message","用户名不能为空！");
            return map;
        }
        if(user.getPassword()==null||user.getPassword().equals("")){
            map.put("code",20000);
            map.put("message","密码不能为空！");
            return map;
        }
        switch (anyUserService.login(user)){
            case 2:
                map.put("code",20000);
                map.put("message","用户不存在！");
                return map;
            case 3:
                map.put("code",20000);
                map.put("message","密码错误！");
                return map;
            case 1:
                String token=JwtUtils.generateToken(user.getId());
                map.put("code",10000);
                map.put("token",token);
                //当前用户token存入缓存，充当session
                redisTemplate.opsForValue().set(RedisUtil.TOKEN_HEAD+user.getId(),token,JwtUtils.EXPIRE_TIME);
                return map;
        }
        return null;
    }
}
