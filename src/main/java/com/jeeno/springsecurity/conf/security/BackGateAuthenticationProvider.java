package com.jeeno.springsecurity.conf.security;

import com.jeeno.springsecurity.pojo.UserDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 后门登陆的自定义认证处理器
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/26 16:51
 */
@Data
@Slf4j
@Component
public class BackGateAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("#BackGateAuthenticationProvider#");
        String username = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!(userDetails instanceof UserDO)) {
            return null;
        }
        UserDO user = (UserDO) userDetails;
        return new BackGateAuthentication(user, user.getAuthorities());
    }

    /**
     *  只对后门登录进行认证验证
     * @param authentication 待认证信息
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.getName().equals(BackGateAuthentication.class.getName());
    }


}
