package com.ada.blog.controller.admin;

import com.ada.blog.entity.AdminUser;
import com.ada.blog.service.AdminUserService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :UserController
 * @date 2020/12/7 22:59
 * @Description: 用户管理模块控制层
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private AdminUserService adminUserService;

    /***
     * @Author Ada
     * @Date 23:05 2020/12/7
     * @Param [request]
     * @return java.lang.String
     * @Description 用户首页
     **/
    @RequestMapping("/user")
    public String versionPage(HttpServletRequest request) {
        request.setAttribute("path", "user");
        return "admin/user/index";
    }


    /***
     * @Author Ada
     * @Date 22:22 2020/12/8
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 列表
     **/
    @GetMapping("/user/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(adminUserService.getUserList(pageUtil));
    }


    /***
     * @Author Ada
     * @Date 23:33 2021/1/1
     * @Param [userId, loginUserName, password, nickName]
     * @return com.ada.blog.util.ResultUtil
     * @Description 添加用户
     **/
    @RequestMapping("/user/add")
    @ResponseBody
    public ResultUtil add(@RequestParam("loginUserName") String loginUserName,
                          @RequestParam("loginPassword") String loginPassword,
                          @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginPassword) || StringUtils.isEmpty(loginUserName)) {
            ResultStatusUtil.failResult("参数异常");
        }

        AdminUser user = new AdminUser();
        user.setLoginUserName(loginUserName);
        user.setLoginPassword(loginPassword);
        user.setNickName(nickName);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        Boolean isSuccess = adminUserService.addUser(user);
        return ResultStatusUtil.successResult(isSuccess);
    }

    /***
     * @Description 获取详情
     *
     * @param id
     * @param request
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 18:17 2021/1/2
     **/
    @RequestMapping("/user/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id, HttpServletRequest request) {
        AdminUser user = adminUserService.selectUserById(id);
        request.setAttribute("user", user);
        return ResultStatusUtil.successResult(user);
    }

    /***
     * @Description 更新用户
     *
     * @param adminUserId
     * @param loginUserName
     * @param loginPassword
     * @param nickName
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 17:54 2021/1/2
     **/
    @PostMapping("/user/update")
    @ResponseBody
    public ResultUtil update(@RequestParam("adminUserId") Integer adminUserId,
                             @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("loginPassword") String loginPassword,
                             @RequestParam("nickName") String nickName) {

        AdminUser user = adminUserService.selectUserById(adminUserId);
        if (user == null) {
            return ResultStatusUtil.failResult("无数据！");
        }
        if (StringUtils.isEmpty(loginPassword) || StringUtils.isEmpty(loginUserName)) {
            ResultStatusUtil.failResult("参数异常");
        }

        user.setLoginUserName(loginUserName);
        user.setLoginPassword(loginPassword);
        user.setNickName(nickName);
        user.setUpdateTime(new Date());
        adminUserService.updateUser(user);
        return ResultStatusUtil.successResult();
    }


    /***
     * @Description 删除
     *
     * @param ids
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 19:00 2021/1/2
     **/
    @RequestMapping("/user/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常");
        }

        if (adminUserService.deleteBatchUser(ids)) {

            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }

}
