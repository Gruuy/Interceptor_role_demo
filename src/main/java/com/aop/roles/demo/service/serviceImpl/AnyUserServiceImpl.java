package com.aop.roles.demo.service.serviceImpl;

import com.aop.roles.demo.dao.AnyUserMapper;
import com.aop.roles.demo.entity.User;
import com.aop.roles.demo.service.AnyUserService;
import com.aop.roles.demo.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnyUserServiceImpl implements AnyUserService {

    @Autowired
    private AnyUserMapper anyUserMapper;

    /**
     * 通过用户名获取用户信息
     */
    @Override
    public User getUserByName(String username) {
        return anyUserMapper.getUserByUsername(username);
    }

    /**
     * 校验登陆信息
     * @return :
     * 1    登陆成功
     * 2    用户不存在
     * 3    密码错误
     */
    @Override
    public Integer login(User user) {
        User dbuser=getUserByName(user.getUsername());
        //传递应用，所以在这里改变这个对象的属性
        user.setId(dbuser.getId());
        if(dbuser==null){
            return 2;
        }
        if(!MD5Utils.checkPassword(dbuser,user.getPassword())){
            return 3;
        }
        return 1;
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    @Override
    public User getUserByPrimaryKey(Integer userId) {
        return anyUserMapper.getUserByUserId(userId);
    }
}
