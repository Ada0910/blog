package com.ada.blog.util;

import com.itextpdf.text.pdf.BaseFont;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Ada
 * @ClassName :PdfUtil
 * @date 2020/3/18 23:36
 * @Description:
 */
public class PdfUtil {
    /**
     * 基础路径
     */
    private static String BASE_PATH;
    /**
     * 存放PDF临时路径
     */
    private static String PDF_TEMP_PATH;
    /**
     * 本地路径
     * 云服务器请用下面
     * private static  String FONT_PATH = "/blog/static/font";
     */
    private static String FONT_PATH = "\\common\\dist\\fonts\\";


    public PdfUtil() {
        BASE_PATH = this.getClass().getResource("/").getPath();
        BASE_PATH = new File(BASE_PATH).getParentFile().getPath();
        PDF_TEMP_PATH = BASE_PATH + "\\" + "pdf\\";
        File filePath = new File(PDF_TEMP_PATH);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
    }

    /***
     * @Author Ada
     * @Date 23:29 2020/3/19
     * @Param [htmlCode, fileName]
     * @return void
     * @Description 创建PDF文件到服务器
     **/
    public void createPdf(String blogContent, String fileName) {
        try {
            File file = new File(PDF_TEMP_PATH + fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            ITextRenderer renderer = new ITextRenderer();
            /** 解决中文支持问题*/
            ITextFontResolver fontResolver = renderer.getFontResolver();
            /**本地font文件加载*/
            fontResolver.addFont(getLocalStaticUrl() + FONT_PATH + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            /**
             * 云服务请用这个
             * fontResolver.addFont( FONT_PATH + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
             */
            renderer.setDocumentFromString(blogContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /***
     * @Author Ada
     * @Date 23:28 2020/3/19
     * @Param [fileName, response]
     * @return void
     * @Description 从服务器上下载PDF
     **/
    public void downLoadPdf( HttpServletResponse response,String fileName) {
        File pdfFile = new File(PDF_TEMP_PATH + fileName);
        try {
            /**将文件读取到内存*/
            FileInputStream fileInputStream = new FileInputStream(PDF_TEMP_PATH + fileName);
            response.reset();
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            /**在浏览器提示用户是保存还是下载*/
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            /**根据个人需要,这个是下载文件的类型*/
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("content-type", "application/pdf");
            /**告诉浏览器下载文件的大小*/
            response.setHeader("Content-Length", String.valueOf(pdfFile.length()));
            ServletOutputStream outputStream = response.getOutputStream();
            /**输出*/
            int len = 1;
            byte[] bs = new byte[1024];
            while ((len = fileInputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /***
     * @Author Ada
     * @Date 23:28 2020/3/19
     * @Param [fileName]
     * @return void
     * @Description 删除PDF
     **/
    public void deletePdf(String fileName) {
        File file = new File(PDF_TEMP_PATH + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /***
     * @Author Ada
     * @Date 15:59 2020/03/21
     * @Param []
     * @return java.lang.String
     * @Description 获取本地static的真实路径
     **/
    public String getLocalStaticUrl() {
        String path = null;
        try {
            /**获取静态资源staic的正式路径*/
            String serverpath = ResourceUtils.getURL("classpath:static").getPath().replace("%20", " ").replace('/', '\\');
            /**从路径字符串中取出工程路径*/
            path = serverpath.substring(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;
    }


}
