package com.jeeno.springsecurity.conf.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义的后门登录认证
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/26 16:48
 */
public class BackGateAuthentication extends AbstractAuthenticationToken {

    /**
     * 用户名/认证后的用户信息
     */
    private Object principle;

    public BackGateAuthentication(Object principle) {
        super(null);
        this.principle = principle;
        super.setAuthenticated(false);
    }

    public BackGateAuthentication(Object principle, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principle = principle;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }
}
