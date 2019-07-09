package com.ada.blog.controller.admin;

import com.ada.blog.service.CommentService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :CommentController
 * @date 2019/7/9 22:58
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping("/comment")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "comment");
        return "admin/comment/comment";
    }

    /***
     * @Author Ada
     * @Date 23:26 2019/7/9
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 评论的 Json串
     **/
    @RequestMapping("/comment/list")
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常！！！");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(commentService.getCommentPage(pageUtil));
    }
}
