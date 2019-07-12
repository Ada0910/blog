package com.ada.blog.controller.admin;

import com.ada.blog.entity.UploadPath;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Ada
 * @ClassName :UploadController
 * @date 2019/7/12 23:08
 * @Description: 上传图片的controller
 */
@Controller
@RequestMapping("/admin")
public class UploadController {


    /***
     * @Author Ada
     * @Date 23:14 2019/7/12
     * @Param [request, file]
     * @return com.ada.blog.util.ResultUtil
     * @Description 上传图片
     **/
    @RequestMapping({"/upload/file"})
    @ResponseBody
    public ResultUtil upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        //文件名
        String fileName = file.getOriginalFilename();
        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        /**生成文件名的通用方法*/
        //日期格式化函叔
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        //随机数
        Random random = new Random();
        StringBuilder tempName = new StringBuilder();
        //构造新的文件名
        tempName.append(simpleDateFormat.format(new Date())).append(random.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

        File filePath = new File(UploadPath.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(UploadPath.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!filePath.exists()) {
                if (!filePath.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + filePath);
                }
            }
            //可以使用该文件将上传文件保存到一个目标文件中
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultUtil resultUtil = ResultStatusUtil.successResult();
        return resultUtil;
    }
}
