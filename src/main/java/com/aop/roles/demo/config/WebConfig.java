package com.aop.roles.demo.config;

import com.aop.roles.demo.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类
 *
 * @author Gruuy
 * @date 2019-7-18
 */
@Configuration
@DependsOn("tokenFilter")
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private TokenFilter tokenFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter)
                // addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
                .addPathPatterns("/**");
                // excludePathPatterns 表示改路径不用拦截
        super.addInterceptors(registry);
    }
}