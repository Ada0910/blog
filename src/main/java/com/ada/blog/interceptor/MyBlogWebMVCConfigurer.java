package com.ada.blog.interceptor;

import com.ada.blog.entity.UploadPath;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login","/admin/kaptcha");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + UploadPath.FILE_UPLOAD_DIC);
    }
}
