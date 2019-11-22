package com.jeeno.springsecurity.pojo;

import com.jeeno.springsecurity.common.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户信息类
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 15:41
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
@Builder
@ToString
public class UserDO implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 用户角色组成的字符串，“,”分隔
     */
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        if (StringUtils.isNotBlank(role)) {
            String[] arr = role.split(SystemConstants.ROLE_SPLIT_CHAR);
            for (String x : arr) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SystemConstants.ROLE_PREFIX + x);
                roles.add(authority);
            }
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
