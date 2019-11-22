package com.jeeno.springsecurity.conf.security;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户注销登录的处理器
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 17:43
 */
@Component
@Slf4j
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        log.info("------------执行登出------------");
        // 清空session
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
        }
        // 返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(ReturnDTO.builder().status(ReturnDTO.StatusEnum.SUCCESS).message("登出成功").build());
        response.getWriter().flush();
    }
}
