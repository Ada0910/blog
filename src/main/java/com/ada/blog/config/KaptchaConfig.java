package com.ada.blog.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author Ada
 * @ClassName :KaptchaConfig
 * @date 2019/6/10 22:47
 * @Description:
 */

@Component //该注解是：该类可以注入到spring容器中
public class KaptchaConfig {

    /**
     * @return com.google.code.kaptcha.impl.DefaultKaptcha
     * @Author Ada
     * @Date 22:55 2019/6/10
     * @Param []
     * @Description 将Kaptcha注入到IOC容器中，return一个设置好属性的实例
     **/
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.image.width", "150");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.session.key", "verifyCode");
        properties.put("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
