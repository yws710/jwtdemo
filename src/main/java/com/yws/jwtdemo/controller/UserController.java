package com.yws.jwtdemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yws.jwtdemo.entity.User;
import com.yws.jwtdemo.exception.BusinessException;
import com.yws.jwtdemo.service.UserService;
import com.yws.jwtdemo.shiro.JwtUtil;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password) {
        User user = userService.getUser(username);
        if (user.getPassword().equals(password)) {
            return JwtUtil.sign(username, password);
        } else {
            //int n = 10 / 0;
            throw new BusinessException(-1, "登录失败");
        }
    }

    /**
     * 所有人都可访问<br>
     * 但是用户与游客看到的内容不一样
     *
     * @return
     */
    @GetMapping("article")
    public String article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "You are already logged in";
        } else {
            return "You are guest";
        }
    }

    /**
     * 登录的用户才可以访问
     *
     * @return
     */
    @GetMapping("require_auth")
    @RequiresAuthentication
    public String requireAuth() {
        return "You are authenticated";
    }

    /**
     * admin角色才可以访问
     *
     * @return
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public String requireRole() {
        return "You are visiting require_role";
    }

    /**
     * 拥有view和edit权限的用户才可以访问
     *
     * @return
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public String requirePermission() {
        return "You are visiting permission require edit,view";
    }
}
