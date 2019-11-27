package com.jeeno.springsecurity.filter;

import com.jeeno.springsecurity.conf.security.BackGateAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后门登录校验的相关过滤器
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/26 16:35
 */
@Slf4j
public class BackGateLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static String PATH = "/back/gate/";


    public BackGateLoginFilter() {
        // 该过滤器所匹配的请求url配置
        super(new AntPathRequestMatcher(PATH + "*", "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("#BackGateLoginFilter#");
        String username = request.getRequestURI().substring(PATH.length());
        BackGateAuthentication backGateAuthentication =new BackGateAuthentication(username);
        return this.getAuthenticationManager().authenticate(backGateAuthentication);
    }

}
