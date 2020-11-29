package com.ada.blog.controller.blog;

import com.ada.blog.entity.Blog;
import com.ada.blog.service.BlogService;
import com.ada.blog.util.MarkDownUtil;
import com.ada.blog.util.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Ada
 * @ClassName :IndexPdfController
 * @date 2020/3/18 23:22
 * @Description:
 */
@Controller
public class IndexPdfController {

    @Autowired
    private BlogService blogService;

    /**
     * @return void
     * @Author Ada
     * @Date 15:02 2020/03/21
     * @Param [request, response]
     * @Description 保存PDF到本地
     **/
    @RequestMapping("/blog/pdfDownload/{blogId}")
    public void pdfDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable("blogId") String blogId) throws UnsupportedEncodingException, InterruptedException {
        String suffixName = ".pdf";
        Blog blog = blogService.getBlogById(Long.parseLong(blogId));
        String blogContent = blog.getBlogContent();
        String pdfUrl = "http://www.isada.cn/upload/pdf/";
        String fileName = blog.getBlogTitle() + suffixName;
        PdfUtil pdfUtil = new PdfUtil();
        pdfUtil.createPdf(addHtmlTag(blogContent), fileName);
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pdfUtil.downloadFile(response,pdfUrl + fileName,  "GET", fileName);
    }

    /***
     * @Author Ada
     * @Date 22:47 2020/3/22
     * @Param [blogContent]
     * @return java.lang.String
     * @Description 添加HTML标签
     **/
    public String addHtmlTag(String blogContent) {
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                .append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
                .append("<style type=\"text/css\">img {width: 700px;}</style>")
                .append("</head>")
                .append("<body>");
        html.append(MarkDownUtil.mdToHtml(blogContent));
        html.append("</body></html>");
        return html.toString();
    }


}
