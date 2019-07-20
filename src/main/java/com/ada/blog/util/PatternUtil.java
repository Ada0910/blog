package com.ada.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ada
 * @ClassName :PatternUtil
 * @date 2019/7/20 16:11
 * @Description:
 */
public class PatternUtil {

    /**
     * 校验邮箱
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Boolean validKeyword(String keyword) {
        String regex = "^[a-zA-Z0-9\\u4E00-\\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(keyword);
        return matcher.matches();
    }


    /**
     * 判断是否是邮箱
     */
    public static boolean isEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * 判断是否是网址
     */
    public static boolean isURL(String urlString) {
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(urlString).matches()) {
            return true;
        } else {
            return false;
        }
    }
}
