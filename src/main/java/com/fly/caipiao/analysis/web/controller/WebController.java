package com.fly.caipiao.analysis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author baidu
 * @date 2018/6/20 下午4:55
 * @description ${TODO}
 **/

@Controller
@RequestMapping("/web")
public class WebController {

    @RequestMapping("/home")
    public String go2Add() {
        return "index";
    }

    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "pages/index";
    }

    @RequestMapping("/test")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("home");
        return mv;
    }
}
