package com.jeeno.springsecurity.conf;

import com.jeeno.springsecurity.conf.security.BackGateAuthenticationProvider;
import com.jeeno.springsecurity.conf.security.LogoutHandler;
import com.jeeno.springsecurity.conf.security.MyAccessDeniedHandler;
import com.jeeno.springsecurity.conf.security.MyFailureHandler;
import com.jeeno.springsecurity.conf.security.MySuccessHandler;
import com.jeeno.springsecurity.filter.BackGateLoginFilter;
import com.jeeno.springsecurity.filter.PassDecoderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring security 核心配置类
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/21 15:14
 */
@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MySuccessHandler successHandler;

    @Resource
    private MyFailureHandler failureHandler;

    @Resource
    private MyAccessDeniedHandler deniedHandler;

    @SuppressWarnings("unused")
    @Resource
    private LogoutHandler logoutHandler;

    @Resource(name = "PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PassDecoderFilter decoderFilter;

    @Resource
    private BackGateAuthenticationProvider backGateAuthenticationProvider;

    /**
     * 认证管理器
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        // 注册自定义的认证器
        auth.authenticationProvider(backGateAuthenticationProvider);
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

        // 后门登录过滤器
        BackGateLoginFilter backGateLoginFilter = new BackGateLoginFilter();
        backGateLoginFilter.setAuthenticationManager(authenticationManagerBean());
        // 对于自定义的认证过滤器，必须要设置成功和失败的处理器。否则redis中会插入一条SpringSecurity_Last_exception,且页面上返回空
        backGateLoginFilter.setAuthenticationFailureHandler(failureHandler);
        backGateLoginFilter.setAuthenticationSuccessHandler(successHandler);

        // 过滤器链配置
        http.addFilterBefore(decoderFilter, UsernamePasswordAuthenticationFilter.class)
            // 后门登录过滤器
            .addFilterBefore(backGateLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 登录及其页面拦截配置
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login")
                // 登录成功处理器, 不同于xxSuccessUrl（跳转url）
                .successHandler(successHandler)
                // 登录失败处理器
                .failureHandler(failureHandler)
                .permitAll()
            .and()
                .authorizeRequests()
                // GET的部分请求路径放行
                .antMatchers(HttpMethod.GET, "/index", "/expire", "/timeout")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/back/gate/*", "/back/gate2/*")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                // 权限校验不通过时调用的处理器（需要已登录，即有认证信息，但是无权限）
                .accessDeniedHandler(deniedHandler);

        // 会话管理
        http.sessionManagement()
                // session失效（登录超时）时请求的url（需要配置无需认证的url，斗则会被拦截到登录页面）
                //     前后端分离的情况下会导致页面跳转，可以考虑用InvalidSessionStrategy
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
