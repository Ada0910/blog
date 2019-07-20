package com.ada.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author Ada
 * @ClassName :MarkDownUtil
 * @date 2019/7/19 23:23
 * @Description:
 */
public class MarkDownUtil {

    /***
     * @Author Ada
     * @Date 23:59 2019/7/19
     * @Param [markdownString]
     * @return java.lang.String
     * @Description md转化为html
     **/
    public static String mdToHtml(String markdownString) {

        if (StringUtils.isEmpty(markdownString)) {
            return "";
        }
        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        return content;
    }
}
