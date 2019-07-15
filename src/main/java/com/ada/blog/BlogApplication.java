package com.ada.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.util.TimeZone;

@MapperScan("com.ada.blog.mapper")
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);

    }

    /*@PostConstruct
    void setDefaultTimezone(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }*/

}
