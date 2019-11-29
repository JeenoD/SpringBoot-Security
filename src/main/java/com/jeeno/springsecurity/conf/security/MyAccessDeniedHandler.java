package com.jeeno.springsecurity.conf.security;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验权限失败的处理器
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/27 14:11
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException{
        log.info("----------权限校验不通过-----------");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(ReturnDTO.<String>builder()
                .status(ReturnDTO.StatusEnum.SUCCESS)
                .data(null)
                .message("暂无权限").build());
        response.getWriter().flush();
    }
}
