package com.ada.blog.util;

import org.springframework.util.StringUtils;

/**
 * @author Ada
 * @ClassName :ResultStatusUtil
 * @date 2019/6/29 17:32
 * @Description: 响应结果生成工具
 */
public class ResultStatusUtil {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    /**
     * 成功
     */
    public static ResultUtil successResult() {
        ResultUtil result = new ResultUtil();
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setResultCode(RESULT_CODE_SUCCESS);
        return result;
    }

    /**
     * 成功
     */
    public static ResultUtil successResult(String message) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功
     */
    public static ResultUtil successResult(Object data) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static ResultUtil failResult(String message) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SERVER_ERROR);
        if (StringUtils.isEmpty(message)) {
            result.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            result.setMessage(message);
        }
        return result;
    }

    /**
     * 错误
     */
    public static ResultUtil errorResult(int code, String message) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(code);
        result.setMessage(message);
        return result;
    }
}
