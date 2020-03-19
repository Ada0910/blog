package com.ada.blog.util;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Ada
 * @ClassName :PdfUtil
 * @date 2020/3/18 23:36
 * @Description:
 */
public class PdfUtil {
    /**基础路径*/
    private static String BASE_PATH;
    /**存放PDF临时路径*/
    private static String PDF_TEMP_PATH;

    public PdfUtil(){
        BASE_PATH = this.getClass().getResource("/").getPath();
        BASE_PATH = new File(BASE_PATH).getParentFile().getPath();
        PDF_TEMP_PATH = BASE_PATH + "\\" + "pdf\\";
        File filePath = new File(PDF_TEMP_PATH);
        if (!filePath.exists()){
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
    public void createPdf(String htmlCode, String fileName){
        try{
            File file = new File(PDF_TEMP_PATH + fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlCode);
            renderer.layout();
            renderer.createPDF(outputStream);
        }
        catch(Exception e){
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
    public void downLoad(String fileName, HttpServletResponse response){
        try {

            FileInputStream fileInputStream = new FileInputStream(PDF_TEMP_PATH + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("content-disposition","attachment;filename=MyPDF.pdf");
            response.setHeader("content-type", "application/pdf");
            //输出
            int len = 1;
            byte[] bs = new byte[1024];
            while((len = fileInputStream.read(bs)) != -1){
                outputStream.write(bs, 0, len);
            }
            fileInputStream.close();
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
    public void deletePdf(String fileName){
        File file = new File(PDF_TEMP_PATH + fileName);
        if (file.exists()){
            file.delete();
        }
    }





}
