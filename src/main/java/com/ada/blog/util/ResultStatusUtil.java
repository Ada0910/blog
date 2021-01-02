package com.ada.blog.util;

import org.springframework.util.StringUtils;

/**
 * @author Ada
 * @ClassName :ResultStatusUtil
 * @date 2019/6/29 17:32
 * @Description: 响应结果状态生成工具
 */
public class ResultStatusUtil {

    private static final String DEFAULT_SUCCESS_MESSAGE = "成功";
    private static final String DEFAULT_FAIL_MESSAGE = "服务器内部出现了错误";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    /***
     * @Description 设置状态和返回信息
     *
     * @param code 状态码
     * @param message 信息
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 23:54 2021/1/1
     **/
    public static ResultUtil setCodeAndMsg(int code, String message,Object data) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /***
     * @Description 成功状态
     *
     * @param
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 18:21 2021/1/2
     **/
    public static ResultUtil successResult() {
        ResultUtil result = new ResultUtil();
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setResultCode(RESULT_CODE_SUCCESS);
        return result;
    }


    public static ResultUtil successResult(String message) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);
        return result;
    }


    public static ResultUtil successResult(Object data) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static ResultUtil successResult(Object data,String message) {
        ResultUtil result = new ResultUtil();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /***
     * @Description  失败
     *
     * @param
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 18:06 2021/1/2
     **/
    public static ResultUtil failResult() {
        ResultUtil result = new ResultUtil();
        result.setMessage(DEFAULT_FAIL_MESSAGE);
        result.setResultCode(RESULT_CODE_SERVER_ERROR);
        return result;
    }


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

    public static ResultUtil failResult(String message,Object data) {
        ResultUtil result = new ResultUtil();
        result.setMessage(message);
        result.setResultCode(RESULT_CODE_SERVER_ERROR);
        result.setData(data);
        return result;
    }



}
