package com.jeeno.springsecurity.conf.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效的策略处理类
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/12/2 9:26
 */
@Slf4j
@Component
public class SessionInvalidStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("此用户登录超时，请重新登录！");
        onSessionInvalid(request, response);
    }
}
