package com.ada.blog.controller.blog;

import com.ada.blog.util.PdfUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ada
 * @ClassName :IndexPdfController
 * @date 2020/3/18 23:22
 * @Description:
 */
@Controller
public class IndexPdfController {

    @RequestMapping("/blog/pdfDownload")
    public void pdfDownload(HttpServletRequest request, HttpServletResponse response) {
        PdfUtil pdfUtil = new PdfUtil();
        String html=getHtmlCode() ;
        String fileName = "MyPDF.pdf";
        pdfUtil.createPdf(html, fileName);
        pdfUtil.downLoad(fileName, response);
        pdfUtil.deletePdf(fileName);

    }
    private String getHtmlCode(){
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                .append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
                .append("<style type=\"text/css\">img {width: 700px;}</style>")
                .append("</head>")
                .append("<body>");
        html.append("<h1>这是一个PDF文档</h1>");
        html.append("<table>");
        html.append("<tr>");
        html.append("<td>第一列</td>");
        html.append("<td>第二列</td>");
        html.append("<td>第三列</td>");
        html.append("<td>第四列</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("</body></html>");
        return html.toString();
    }



}
