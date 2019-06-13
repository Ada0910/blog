package com.ada.blog.controller.admin;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Ada
 * @ClassName :KaptchaController
 * @date 2019/6/10 23:21
 * @Description:
 */
@Controller
public class KaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/admin/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();

        try {
            //生成验证码字符串并保存在session里面
            String verifyCode = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage image = defaultKaptcha.createImage(verifyCode);
            ImageIO.write(image, "jpg", imageOutputStream);

        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;

        }

        captchaOutputStream = imageOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        servletOutputStream.write(captchaOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}