package com.ada.blog.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Ada
 * @ClassName :ResultUtil
 * @date 2019/6/27 23:29
 * @Description: 将结果序列化
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResultUtil<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int resultCode;
    private String message;
    private T data;

    public ResultUtil(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
}
