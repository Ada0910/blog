package com.ada.blog.controller.admin;

import com.ada.blog.util.MyBlogUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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

    @Value("${FILE_UPLOAD_DIC}")
    private  String FILE_UPLOAD_DIC;


    /***
     * @Author Ada
     * @Date 23:14 2019/7/12
     * @Param [request, file]
     * @return com.ada.blog.util.ResultUtil
     * @Description 上传图片
     **/
    @RequestMapping({"/upload/file"})
    @ResponseBody
    public ResultUtil upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {
        //文件名
        String fileName = file.getOriginalFilename();
        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        /**生成文件名的通用方法*/
        //日期格式化函叔
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //随机数
        Random random = new Random();
        StringBuilder tempName = new StringBuilder();
        //构造新的文件名
        tempName.append(simpleDateFormat.format(new Date())).append(random.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

        File filePath = new File(FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(FILE_UPLOAD_DIC + newFileName);
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
        resultUtil.setData(MyBlogUtil.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName);
        return resultUtil;
    }


    /***
     * @Author Ada
     * @Date 21:28 2019/7/16
     * @Param [request, response, file]
     * @return void
     * @Description 编译器里的上传图片功能
     **/
    @RequestMapping(value = "/blog/md/uploadfile", method = RequestMethod.POST)
    public void uploadFileByEditor(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(name = "editormd-image-file", required = true) MultipartFile file) throws URISyntaxException, IOException {

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(random.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

        File destFile = new File(FILE_UPLOAD_DIC + newFileName);
        String fileUrl = MyBlogUtil.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName;
        File filePath = new File(FILE_UPLOAD_DIC);
        try {
            if (!filePath.exists()) {
                if (!filePath.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + filePath);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (UnsupportedEncodingException e) {
            response.getWriter().write("{\"success\":0}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }

    }


}
