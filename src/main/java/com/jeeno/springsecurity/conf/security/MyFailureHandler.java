package com.jeeno.springsecurity.conf.security;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 15:32
 */
@Component
@Slf4j
public class MyFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        log.info("---------------登录失败---------------");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(ReturnDTO.builder().status(ReturnDTO.StatusEnum.FAILURE).message("登录失败").build());
        response.getWriter().flush();
    }
}
