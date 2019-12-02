package com.jeeno.springsecurity.conf.session;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话策略的抽象类
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/12/2 9:51
 */
@Slf4j
public class AbstractSessionStrategy {

    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String message = "此用户登录超时，请重新登录";
        if (isConcurrency()) {
            message = "用户在异地登录，请重新登录！";
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(message);
        response.getWriter().flush();
    }

    /**
     * session失效是否是并发导致的
     */
    protected boolean isConcurrency() {
        return false;
    }

}
