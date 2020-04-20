package com.ada.blog.util;

import com.itextpdf.text.pdf.BaseFont;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * @author Ada
 * @ClassName :PdfUtil
 * @date 2020/3/18 23:36
 * @Description:
 */
public class PdfUtil {

    /**
     * 存放PDF临时路径(云服务器)
     */
    private static String PDF_TEMP_PATH= "/upload/pdf/";
    /**
     * 本地路径
     * 云服务器请用下面
     * private static  String FONT_PATH = "/upload/font/";
     */
   private static String FONT_PATH = "\\common\\dist\\fonts\\";
   // private static  String FONT_PATH = "/upload/font/";


    public PdfUtil() {
      PDF_TEMP_PATH = this.getClass().getResource("/").getPath() + "\\" + "pdf\\";
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
            //fontResolver.addFont( FONT_PATH + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(blogContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            System.out.println(">>>>>>>>>"+e.getMessage());
        }
    }


    /***
     * @Author Ada
     * @Date 23:28 2020/3/19
     * @Param [fileName]
     * @return void
     * @Description 删除PDF
     **/
    @Scheduled(cron = "* * 0/2 * * ?")
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

    /***
     * @Author Ada
     * @Date 23:43 2020/4/19
     * @Param [response, url, method, fileName]
     * @return void
     * @Description 下载pdf
     **/
    public  void downloadFile(HttpServletResponse response,String url, String method, String fileName) {
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            /**在浏览器提示用户是保存还是下载*/
            response.setHeader("Content-Disposition", "attachment; filename=" +  fileName);
            /**根据个人需要,这个是下载文件的类型*/
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("content-type", "application/pdf");
            /**告诉浏览器下载文件的大小*/
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                outputStream.write(buf, 0, length);
                length = bis.read(buf);
            }
            outputStream.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
    }

}
