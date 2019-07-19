package com.aop.roles.demo.filter;

import com.alibaba.fastjson.JSONObject;
import com.aop.roles.demo.entity.Role;
import com.aop.roles.demo.entity.User;
import com.aop.roles.demo.service.AnyUserService;
import com.aop.roles.demo.service.UrlService;
import com.aop.roles.demo.util.ErrorMessage;
import com.aop.roles.demo.util.JwtUtils;
import com.aop.roles.demo.util.RedisUtil;
import com.aop.roles.demo.util.ResponseUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器校验token
 * @author Gruuy
 * @date 2019-7-18
 */
@Component
@Scope("prototype")
public class TokenFilter implements HandlerInterceptor {

    Logger log= LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private AnyUserService anyUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断进入的链接是否在拦截链中
        if(checkUrl(request.getServletPath())){
            return true;
        }
        //获取token
        String token=request.getHeader("token");
        //校验
        if(token==null||token.trim().equals("")){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code","20000");
            jsonObject.put("error_msg","token不能为空！");
            ResponseUtil.repsonseSetInfo(response,jsonObject);
            return false;
        }
        // 获取当前时间的毫秒值
        long start = System.currentTimeMillis();
        String user = null;
        // 解析token
        try {
            user = Jwts.parser()
                    .setSigningKey(JwtUtils.PRI_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            long end = System.currentTimeMillis();
            log.info("执行时间为：{}" , (end - start) + "毫秒");
            log.info("用户id为：{}",user);
            log.info("token:{}",token);
            if (user == null || user .equals("")){
                ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("User is Null！"));
                return false;
            }
        } catch (ExpiredJwtException e) {
            ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("token已过期！"));
            return false;
        } catch (UnsupportedJwtException e) {
            ResponseUtil.repsonseSetInfo(response, new ErrorMessage().fail("Token格式错误！"));
            return false;
        } catch (MalformedJwtException e) {
            ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("token没有被正确构造！"));
            return false;
        } catch (SignatureException e) {
            ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("签名失败！"));
            return false;
        } catch (IllegalArgumentException e) {
            ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("非法参数异常！"));
            return false;
        }
        //校验token与当前登陆用户的缓存是否一致
        String userNowToken=redisTemplate.opsForValue().get(RedisUtil.TOKEN_HEAD+user).toString();
        if(!token.equals(userNowToken)){
            ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("token已过期！"));
            return false;
        }
        //校验规则
        if(checkRole(request.getServletPath(),user)){
            return true;
        }
        ResponseUtil.repsonseSetInfo(response,new ErrorMessage().fail("权限不足！"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /** 校验Url
     *  如果True即代表访问了需要验证的token
     *  false则url不在保护之内
     * */
    private boolean checkUrl(String url){
        if(freeUrl(url)){
            return true;
        }
        List<Role> roleList=urlService.ListAllMenu();
        Role nowurl=roleList.stream().filter(a->a.getUrl().equals(url)).findAny().orElse(null);
        if(nowurl==null){
            return true;
        }
        return false;
    }

    /** 校验Url
     *  额外配置排除的url
     * */
    private boolean freeUrl(String url){
        if("/customer".equals(url)){
            return true;
        }
        return false;
    }

    /**
     * 规则校验
     * 查询当前用户的角色
     * 再校验当前url需要的角色
     */
    public boolean checkRole(String url,String userId){
        List<Role> urlNeedRole=urlService.ListRoleFromUrl(url);
        User userRole=anyUserService.getUserByPrimaryKey(Integer.valueOf(userId));
        for (Role role:urlNeedRole) {
            for(String userHadRole:userRole.getRole()){
                if(role.getRoleName().equals(userHadRole)){
                    return true;
                }
            }
        }
        return false;
    }
}
