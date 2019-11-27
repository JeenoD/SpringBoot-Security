package com.jeeno.springsecurity.service.impl;

import com.jeeno.springsecurity.pojo.UserDO;
import com.jeeno.springsecurity.repository.UserRepository;
import com.jeeno.springsecurity.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/25 22:41
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDO getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
