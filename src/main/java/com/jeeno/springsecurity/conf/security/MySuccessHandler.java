package com.jeeno.springsecurity.conf.security;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/21 15:24
 */
@Component
@Slf4j
public class MySuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("---------------登录成功---------------");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(ReturnDTO.builder().status(ReturnDTO.StatusEnum.SUCCESS).message("登录成功").build());
        response.getWriter().flush();
    }

}
