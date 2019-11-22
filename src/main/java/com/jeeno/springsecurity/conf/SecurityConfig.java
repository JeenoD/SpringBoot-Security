package com.jeeno.springsecurity.conf;

import com.jeeno.springsecurity.conf.security.LogoutHandler;
import com.jeeno.springsecurity.conf.security.MyFailureHandler;
import com.jeeno.springsecurity.conf.security.MySuccessHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring security 核心配置类
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 15:14
 */
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MySuccessHandler successHandler;

    @Resource
    private MyFailureHandler failureHandler;

    @Resource
    private LogoutHandler logoutHandler;

    @Resource(name = "PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 注销登录
        http.logout()
                // 注销的请求地址
                .logoutUrl("/logout")
                // 自定义注销的逻辑（清空cookie）
                //.logoutSuccessHandler(logoutHandler)
                // 自带API接口：是否清空当前session - 默认（true）
                .invalidateHttpSession(true)
                // 自带API接口： 清空cookies（清空cookie和认证信息）
                .deleteCookies()
                // 自带API接口： 清空权限信息 - 默认（true）
                .clearAuthentication(true);

        // 登录及其页面拦截配置
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/index", "/expire", "/timeout")
                .permitAll()
                .anyRequest()
                .authenticated();

        // 会话管理
        http.sessionManagement()
                // session失效（异地登录/登录超时都会）时请求的url（需要配置无需认证的url，斗则会被拦截到登录页面）
                .invalidSessionUrl("/timeout")
                // 同一用户最大的同时在线数
                .maximumSessions(1)
//                // 是否执行登录保护（true-后续无法登录;false-后续登录挤掉前面用户） 默认false
//                .maxSessionsPreventsLogin(true)
                // 异地登录导致过期时请求的url（需要配置无需认证的url,否则会被拦截到登录页面）
                .expiredUrl("/expire");

        // 允许跨域
        http.cors();

        //暂时关闭csrf功能
        http.csrf().disable();
    }

}
