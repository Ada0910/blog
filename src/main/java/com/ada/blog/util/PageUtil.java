package com.ada.blog.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :PageUtil
 * @date 2019/6/29 23:00
 * @Description: 分页查询(获取页码和条数)
 */

@Getter
@Setter
@ToString
public class PageUtil extends LinkedHashMap<String, Object> {

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int limit;

    public PageUtil(Map<String, Object> params) {
        this.putAll(params);

        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }
}
