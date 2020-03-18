package com.ada.blog.util;

import java.io.File;

/**
 * @author Ada
 * @ClassName :PdfUtil
 * @date 2020/3/18 23:36
 * @Description:
 */
public class PdfUtil {
    private static String BASE_PATH;
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




}
