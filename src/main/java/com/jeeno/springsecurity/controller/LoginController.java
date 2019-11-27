package com.jeeno.springsecurity.controller;

import com.jeeno.springsecurity.common.AppProperties;
import com.jeeno.springsecurity.pojo.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制层接口
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/21 15:35
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    private AppProperties appProperties;

    /**
     * 跳转登录页面的接口
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * 异地登录时的处理逻辑
     * @return ReturnDTO<String>
     */
    @GetMapping("/expire")
    public ModelAndView expired() {
        log.info("此用户在异地登录，请重新登录！");
        return new ModelAndView("/login");
    }

    /**
     * 登录超时的处理逻辑
     * @return ModelAndView
     */
    @GetMapping("/timeout")
    public ModelAndView timeout() {
        log.info("此用户登录超时，请重新登录！");
        return new ModelAndView("/login");
    }

    /**
     * 获取登录用户的信息
     * @param user 用户信息
     * @return UserDO
     */
    @GetMapping("/me")
    public UserDO getUserInfo(@AuthenticationPrincipal UserDO user) {
        return user;
    }

    /**
     * admin角色才能调用的方法
     * @return String
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPermission() {
        return "调用成功,需要有admin角色";
    }

    /**
     * user角色才能调用的方法
     * @return String
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userPermission() {
        return "调用成功，需要有user角色";
    }

    /**
     * root角色才能调用的方法
     * @return String
     */
    @GetMapping("/root")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String rootPermission() {
        return "调用成功，需要有root角色";
    }

    /**
     * 获取session属性
     * @param request 请求
     * @return String
     */
    @GetMapping("/session")
    public String getSessionAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return  appProperties.getPort() + " : " + session.getAttribute("param");
    }

}
