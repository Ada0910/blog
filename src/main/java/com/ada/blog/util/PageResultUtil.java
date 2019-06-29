package com.ada.blog.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ada
 * @ClassName :PageResultUtil
 * @date 2019/6/29 23:16
 * @Description: 分页工具类
 */
@Getter
@Setter
public class PageResultUtil {

    /**
     * 总数
     */
    private int totalCount;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * 列表数据
     */
    private List<?> list;

    /**
     * 分页
     */
    public PageResultUtil(List<?> list, int totalCount, int pageSize, int currentPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = (int) Math.ceil(totalCount / pageSize);
    }
}
