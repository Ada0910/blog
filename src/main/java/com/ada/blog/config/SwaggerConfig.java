package com.ada.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName:SwaggerConfig
 * @author: Ada
 * @date 2020/02/24 17:09
 * @Description: Swaqger配置类
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket blogApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.ada.blog.controller")).paths(PathSelectors.any()).build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("API 文档")
                // 文档描述
                .description("https://github.com/einverne/thrift-swift-demo/tree/master/spring-boot-demo")
                .termsOfServiceUrl("https://github.com/einverne/thrift-swift-demo/tree/master/spring-boot-demo")
                .version("v1")
                .build();
    }
}


