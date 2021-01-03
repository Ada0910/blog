package com.ada.blog.controller.admin;

import com.ada.blog.entity.Role;
import com.ada.blog.service.RoleService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :RoleController
 * @date 2020/12/7 23:05
 * @Description: 角色管理模块
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /***
     * @Description 首页跳转
     *
     * @param request
     * @return java.lang.String
     * @Author Ada
     * @Date 23:41 2021/1/2
     **/
    @RequestMapping("/role")
    public String toIndex(HttpServletRequest request) {
        request.setAttribute("path", "role");
        return "admin/role/role_index";
    }


    /***
     * @Description
     *
     * @param params
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 18:19 2021/1/3
     **/
    @GetMapping("/role/getRoleList")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常");
        }
        PageUtil pageUtil = new PageUtil(params);
        PageResultUtil result = roleService.getRoleList(pageUtil);
        return ResultStatusUtil.successResult(result);
    }



    /***
     * @Description  添加
     *
     * @param roleName
     * @param roleDesc
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 20:22 2021/1/3
     **/
    @RequestMapping("/role/add")
    @ResponseBody
    public ResultUtil add(@RequestParam("roleName") String roleName,
                          @RequestParam("roleDesc") String roleDesc) {
        if (StringUtils.isEmpty(roleName)) {
            ResultStatusUtil.failResult("参数异常");
        }

        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        Boolean isSuccess = roleService.addrole(role);
        return ResultStatusUtil.successResult(isSuccess);
    }


    /***
     * @Description 根据id获取role对象
     *
     * @param id
     * @param request
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 20:20 2021/1/3
     **/
    @RequestMapping("/role/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id, HttpServletRequest request) {
        Role role = roleService.selectRoleById(id);
        request.setAttribute("role", role);
        return ResultStatusUtil.successResult(role);
    }


    /***
     * @Description 更新
     *
     * @param roleId
     * @param roleName
     * @param roleDesc
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 20:22 2021/1/3
     **/
    @PostMapping("/role/update")
    @ResponseBody
    public ResultUtil update(@RequestParam("roleId") Integer roleId,
                             @RequestParam("roleName") String roleName,
                             @RequestParam("roleDesc") String roleDesc) {

        Role role = roleService.selectRoleById(roleId);
        if (role == null) {
            return ResultStatusUtil.failResult("无数据！");
        }
        if (StringUtils.isEmpty(roleName) || roleId < 0) {
            ResultStatusUtil.failResult("参数异常");
        }

        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setUpdateTime(new Date());
        roleService.updaterole(role);
        return ResultStatusUtil.successResult();
    }


    /***
     * @Description  删除
     *
     * @param ids
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 20:23 2021/1/3
     **/
    @RequestMapping("/role/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常");
        }

        if (roleService.deleteBatchRole(ids)) {

            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }


}
