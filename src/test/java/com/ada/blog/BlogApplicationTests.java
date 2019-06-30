package com.ada.blog;

import com.ada.blog.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {

        MD5Util  md5Util = new MD5Util();
        System.out.println("密码为1的md5是：");
        System.out.println(md5Util.MD5Encode("123","UTF-8"));
    }

}
