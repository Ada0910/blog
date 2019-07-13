package com.ada.blog.util;

import java.net.URI;

/**
 * @author Ada
 * @ClassName :MyBlogUtil
 * @date 2019/7/13 14:40
 * @Description:
 */
public class MyBlogUtil {

    /**
     * 获取URL
     */
    public static URI getHost(URI uri) {
        URI tempURI = null;
        try {
            //给定参数来创建URL
            tempURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable throwable) {
            tempURI = null;
        }

        return tempURI;
    }


}
