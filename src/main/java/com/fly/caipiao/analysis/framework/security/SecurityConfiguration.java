package com.fly.caipiao.analysis.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;


/**
 * @author baidu
 * @date 2018/6/19 下午3:55
 * @description ${TODO}
 **/

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private com.fly.caipiao.analysis.framework.security.UserCredentials userCredentials;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()//配置安全策略
//                .antMatchers("/resources/**").permitAll()//定义请求不需要验证
                .antMatchers("/css/**","/js/**","/sb/**","/web/login","/user/login").permitAll()//定义请求不需要验证
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/web/login")
                .permitAll()//定义logout不需要验证
                .and()
                .formLogin()               //使用form表单登录
                .loginPage("/web/login")          // 设置登录页面
                .loginProcessingUrl("/user/login")   // 自定义的登录接口
                .defaultSuccessUrl("/web/index")
                .permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userCredentials); //user Details Service验证
    }



}
