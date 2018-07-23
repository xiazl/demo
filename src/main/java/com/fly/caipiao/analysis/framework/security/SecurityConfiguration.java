package com.fly.caipiao.analysis.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private UserCredentials userCredentials;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()  // 配置安全策略
                .antMatchers("/css/**","/js/**","/sb/**")
                .permitAll()          // 静态资源和不需要验证的URL
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()   //其余的所有请求都需要验证
                .and()
                .logout()
                .logoutUrl("/logout")            // 登出地址
                .logoutSuccessUrl("/web/login")  // 登录成功跳转地址
                .permitAll()
                .and()
                .formLogin()                      // 使用form表单登录
//                .failureUrl("/login?error=true")
                .loginPage("/web/login")          // 跳转登录页面
                .loginProcessingUrl("/admin/login")   // 自定义的登录处理URL
                .defaultSuccessUrl("/web/index")   // 登录成功跳转地址
                .permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userCredentials);  // user验证
    }

}
