package com.aop.roles.demo.util;


import com.aop.roles.demo.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义MD5加密类
 * @author Gruy
 * @date 2019-7-17
 */
public class MD5Utils {

    /** 加密方法 */
    private static final String ENCRYPTIONMETHOD_MD5= "MD5";
    /**
     * salt策略为username + wdnmd
     * 方法作用为加密后返回对象
     * @param user
     * @return
     */
    public static final User MD5Password(User user){
        //指定盐值
        String salt=user.getUsername()+"wdnmd";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSource=ByteSource.Util.bytes(salt);
        //加密    加密方式 密码 盐值 加密次数
        SimpleHash password=new SimpleHash(ENCRYPTIONMETHOD_MD5,user.getPassword(),byteSource,1024);
        user.setPassword(password.toString());
        return user;
    }
    /**
     * 验证密码方法
     * 因为MD5不可逆  那我就再加密看看一样不
     * @param user
     * @param password
     * @return
     */
    public static final boolean checkPassword(User user,String password){
        //指定盐值
        String salt=user.getUsername()+"wdnmd";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSource=ByteSource.Util.bytes(salt);
        //加密    加密方式 密码 盐值 加密次数
        SimpleHash password2=new SimpleHash(ENCRYPTIONMETHOD_MD5,password,byteSource,1024);
        System.out.println(password2.toString() );
        System.out.println(user.getPassword() );
        //进行比对
        if(password2.toString().trim().equals(user.getPassword().trim())){
            return true;
        }
        return false;
    }
}
