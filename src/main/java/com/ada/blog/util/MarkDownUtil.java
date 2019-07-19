package com.ada.blog.util;

import org.springframework.util.StringUtils;

import java.security.cert.Extension;
import java.util.Arrays;

/**
 * @author Ada
 * @ClassName :MarkDownUtil
 * @date 2019/7/19 23:23
 * @Description:
 */
public class MarkDownUtil {

    public static String mdToHtml(String markdownString) {

        if (StringUtils.isEmpty(markdownString)) {
            return "";
        }
        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).builde();
        String content = renderer.render(document);
        return content;
    }
}
