package com.jeeno.springsecurity.conf;

import com.jeeno.springsecurity.conf.security.MyFailureHandler;
import com.jeeno.springsecurity.conf.security.MySuccessHandler;
import com.jeeno.springsecurity.filter.BackGateLoginFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/12/2 18:38
 */
@Component
public class BackGateConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private MyFailureHandler failureHandler;

    @Resource
    private MySuccessHandler successHandler;

    @Override
    public void configure(@NotNull HttpSecurity http) {
        BackGateLoginFilter backGateLoginFilter = new BackGateLoginFilter();
        backGateLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        backGateLoginFilter.setAuthenticationFailureHandler(failureHandler);
        backGateLoginFilter.setAuthenticationSuccessHandler(successHandler);
        backGateLoginFilter.setSessionAuthenticationStrategy(http.getSharedObject(SessionAuthenticationStrategy.class));

        http.addFilterBefore(backGateLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
