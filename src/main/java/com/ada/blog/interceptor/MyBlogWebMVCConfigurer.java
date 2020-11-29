package com.ada.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ada
 * @ClassName :MyBlogWebMVCConfigurer
 * @date 2019/7/22 23:20
 * @Description:
 */
@Configuration
public class MyBlogWebMVCConfigurer implements WebMvcConfigurer {

    @Value("${FILE_UPLOAD_DIC}")
    private  String FILE_UPLOAD_DIC;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login","/admin/kaptcha");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + FILE_UPLOAD_DIC);
    }
}
