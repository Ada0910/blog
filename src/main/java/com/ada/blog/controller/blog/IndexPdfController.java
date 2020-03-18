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
@Controller("/blog")
public class IndexPdfController {

    @RequestMapping("/pdfDownload")
    public void pdfDownload(HttpServletRequest request, HttpServletResponse response) {
        PdfUtil pdfUtil = new PdfUtil();


    }

}
