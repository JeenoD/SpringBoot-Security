package com.jeeno.springsecurity.service;

import com.jeeno.springsecurity.pojo.UserDO;

/**
 * 用户相关的业务接口
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/21 16:24
 */
public interface IUserService {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return UserDO
     */
    UserDO getUserByUsername(String username);
}
