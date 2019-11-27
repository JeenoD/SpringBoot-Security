package com.jeeno.springsecurity.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;

/**
 * 密文解密的过滤器
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/26 14:47
 */
@Component
@Slf4j
public class PassDecoderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String loginUrl = "/user/login";
        String loginMethod = "POST";
        if (StringUtils.equalsIgnoreCase(loginUrl, httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), loginMethod)) {
            log.info("#Filter# 帐号密码登录");
            filterChain.doFilter(new SecurityHttpServletRequestWrapper(httpServletRequest), httpServletResponse);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 请求对象包装类
     */
    private static class SecurityHttpServletRequestWrapper extends HttpServletRequestWrapper {

        SecurityHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return StringUtils.equalsIgnoreCase(SPRING_SECURITY_FORM_PASSWORD_KEY, name) ? decodePassword(value) : value;
        }

        @SneakyThrows(Exception.class)
        private String decodePassword(String password) {
            // 如果前端传输过来的密码是加密的。可以在这里解密。
            log.info("#Filter# 此处可以对密码进行解密");
            return password;
        }
    }


}
