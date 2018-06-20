//package com.fly.caipiao.analysis.framework.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.authentication.UserCredentials;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsUtils;
//
//
///**
// * @author baidu
// * @date 2018/6/19 下午3:55
// * @description ${TODO}
// **/
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    private static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .authorizeRequests()//配置安全策略
//                .antMatchers("/resources/**").permitAll()//定义请求不需要验证
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .anyRequest().authenticated()//其余的所有请求都需要验证
//                .and()
//                .logout()
//                .logoutUrl("/log/logout")
//                .logoutSuccessUrl("/log/index")
//                .permitAll()//定义logout不需要验证
//                .and()
//                .formLogin();   //使用form表单登录
////                .loginPage("/login")
////                .permitAll();
//    }
//
//    @Bean
//    protected UserDetailsService customUserService(){ //注册UserDetailsService 的bean
//        return new UserCredentials();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService()); //user Details Service验证
//    }
//
//
//
//}
