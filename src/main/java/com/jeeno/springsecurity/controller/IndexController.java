package com.jeeno.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jeeno
 * @version 1.0.0
 * @date 2019/11/22 13:03
 */
@Controller
public class IndexController {

    /**
     * 首页地址
     * @return ModelAndView
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
