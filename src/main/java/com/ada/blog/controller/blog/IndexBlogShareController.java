package com.ada.blog.controller.blog;

import com.ada.blog.entity.UploadPath;
import com.ada.blog.util.QRCodeUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Ada
 * @ClassName :IndexBlogShareController
 * @date 2020/3/23 23:39
 * @Description: 博客分享Controller
 */
@Controller
public class IndexBlogShareController {

    /**
     * 生成二维码
     */
    @RequestMapping("/blog/shareToWeChat")
    @ResponseBody
    public ResultUtil shareToWeChat(@RequestParam(value = "content", required = false) String content,
                                    HttpServletRequest requset, @RequestParam("blogId")Long blogId) throws Exception {
        /**文件存放的路径*/
        String fileName = "blog"+blogId;
        /**云服务器请用这个*/
         String path = UploadPath.FILE_UPLOAD_DIC+"blogShare/";
        String suffixName = ".png";
        QRCodeUtil.encodeQRCode(content,path+fileName+suffixName);
        return ResultStatusUtil.successResult("/upload/blogShare/blog"+blogId+suffixName);
    }
}
