package com.fly.caipiao.analysis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author baidu
 * @date 2018/6/20 下午4:55
 * @description ${TODO}
 **/

@Controller
@RequestMapping("/web")
public class WebController {

    @RequestMapping("/login")
    public String go2Add() {
        return "login";
    }


    @RequestMapping("/index")
    public String test() {
        return "index";
    }
}
