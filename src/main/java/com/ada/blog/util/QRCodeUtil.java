package com.ada.blog.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    private static Logger log = LoggerFactory.getLogger(QRCodeUtil.class);

    /***
     * @Author Ada
     * @Date 22:30 2020/3/24
     * @Param [text, path]
     * @return void
     * @Description 生成二维码
     **/
    public static void encodeQRCode(String text, String path) {
        Integer width = 300;
        Integer height = 300;
        String format = "png";
        try {
            /**得到文件对象*/
            File file = new File(path);
            /**判断目标文件所在的目录是否存在*/
            if (!file.getParentFile().exists()) {
                /**如果目标文件所在的目录不存在，则创建父目录*/
                log.info("目标文件所在目录不存在，准备创建它！");
                if (!file.getParentFile().mkdirs()) {
                    log.info("创建目标文件所在目录失败！");
                    return;
                }
            }
            /**设置字符集编码*/
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            /** 生成二维码矩阵*/
            BitMatrix bitMatrix = null;
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            /**二维码保存路径*/
            Path outputPath = Paths.get(path);
            /** 写入文件*/
            MatrixToImageWriter.writeToPath(bitMatrix, format, outputPath);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
