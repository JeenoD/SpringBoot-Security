package com.jeeno.springsecurity.repository;

import com.jeeno.springsecurity.pojo.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户的操作
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 16:21
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {

    /**
     * 根据用户名查找用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDO findByUsername(String username);
}
