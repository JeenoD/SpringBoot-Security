package com.jeeno.springsecurity.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/21 17:14
 */
@Data
@Component
public class AppProperties {

    @Value("${server.port}")
    String port;

}
