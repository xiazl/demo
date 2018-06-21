package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.entity.AdminUser;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author baidu
 * @date 2018/6/20 下午4:55
 * @description ${TODO}
 **/

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result dd(@RequestBody @Valid AdminUser adminUser) {
        userService.add(adminUser);
        return ResponseData.success();
    }

    @RequestMapping("/list")
    public Result list(PageBean pageBean) {
        userService.list(pageBean);
        return ResponseData.success();
    }
}
