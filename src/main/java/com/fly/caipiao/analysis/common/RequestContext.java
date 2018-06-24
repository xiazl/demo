package com.fly.caipiao.analysis.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author baidu
 * @date 2018/6/23 下午10:57
 * @description 用户信息
 **/
public class RequestContext {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public static User getUser(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        User user = (User) auth.getPrincipal();
        return user;
    }
}
