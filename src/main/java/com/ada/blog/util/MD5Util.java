package com.ada.blog.util;

import java.security.MessageDigest;

/**
 * @author Ada
 * @ClassName :MD5Util
 * @date 2019/6/18 22:58
 * @Description:密码加密工具类
 */
public class MD5Util {

    private static String byteArrayHexString(byte b[]) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append(byteToHexString(b[i]));
        }
        return buffer.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    public static String MD5Encode(String origin, String charsetName) {

        String resultString = null;
        try {
            resultString = new String(origin);
            /**返回实现指定摘要算法的MessageDigest对象,digest()返回生成哈希值的字节数组*/
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayHexString(md.digest(resultString.getBytes(charsetName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }


}
