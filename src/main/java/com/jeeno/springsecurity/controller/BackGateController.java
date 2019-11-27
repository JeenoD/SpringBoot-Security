package com.jeeno.springsecurity.controller;

import com.jeeno.springsecurity.pojo.ReturnDTO;
import com.jeeno.springsecurity.pojo.UserDO;
import com.jeeno.springsecurity.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 后门登录的控制层
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/25 22:39
 */
@RestController
@Slf4j
public class BackGateController {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private IUserService userService;

    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 后门登录的接口（此种模拟登录方式，需要明文密码，如果数据库中保存的是不可逆的密文，则不适宜采用）
     * @param username 用户名
     * @param request 请求信息
     * @return ReturnDTO<String>
     */
    @GetMapping("/back/gate2/{username}")
    public ReturnDTO<String> backGateLogin(@PathVariable String username, HttpServletRequest request) {

        UserDO user = userService.getUserByUsername(username);
        if(user == null) {
            return ReturnDTO.<String>builder()
                    .data(null)
                    .message("该用户不存在")
                    .status(ReturnDTO.StatusEnum.FAILURE).build();
        }

        // 模拟一遍自定义认证（需要知道明文密码，现实场景中不可用）
        Authentication auth = new UsernamePasswordAuthenticationToken(username, "123456", user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(auth);

        sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
        // 将认证信息写入到security上下文中，返回响应式，spring security自动会把SecurityContext中的内容写入到session，
        // 并同步进redis用作共享
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("login session: {}", request.getSession().getId());

        return ReturnDTO.<String>builder().status(ReturnDTO.StatusEnum.SUCCESS)
                .message("登录成功")
                .data(null)
                .build();

    }

}
