package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.entity.User;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.UserService;
import com.fly.caipiao.analysis.web.controller.vo.UserPwdVO;
import com.fly.caipiao.analysis.web.controller.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result add(@RequestBody @Valid UserVO userVO) {
        userService.add(userVO);
        return ResponseData.success();
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<User> list(PageBean pageBean, ConditionVO conditionVO) {
        // jquery datatable服务端分页
        return userService.list(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        userService.delete(id);
        return ResponseData.success();
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result update(@RequestBody @Valid UserPwdVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        userService.update(user);
        return ResponseData.success();
    }

    @RequestMapping("/setting")
    public String profile(Integer id) {
        return "user_pwd";
    }

    @ResponseBody
    @RequestMapping("/updatePwd")
    public Result resetPwd(@RequestBody @Valid UserPwdVO pwd) {
        userService.updatePwd(pwd);
        return ResponseData.success();
    }
}
