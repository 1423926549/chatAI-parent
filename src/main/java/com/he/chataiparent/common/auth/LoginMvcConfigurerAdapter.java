package com.he.chataiparent.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginMvcConfigurerAdapter extends WebMvcConfigurationSupport {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
        patterns.add("/user/login");
        patterns.add("/user/register");
        patterns.add("/user/logout");
        registry.addInterceptor(new LoginInterceptor(redisTemplate))
                .addPathPatterns("/**")  // 添加拦截路径
                .excludePathPatterns(patterns);  // 排除路径
        super.addInterceptors(registry);
    }
}
