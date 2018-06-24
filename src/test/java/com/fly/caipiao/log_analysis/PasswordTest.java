package com.fly.caipiao.log_analysis;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;

/**
 * @author baidu
 * @date 2018/6/19 下午4:33
 * @description ${TODO}
 **/
public class PasswordTest {
    private static final BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    public static void main(String[] args){
        String oldPass = passwordEncoder.encode("baidu");
        System.out.println(oldPass);
        System.out.println(passwordEncoder.matches("baidu",oldPass));
        String str = "2011-11-11 21:11:11";
        try {
            System.out.println(DateUtils.parseDate(str,"yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
