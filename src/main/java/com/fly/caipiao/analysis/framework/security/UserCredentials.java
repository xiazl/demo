//package com.fly.caipiao.analysis.framework.security;
//
//import com.fly.caipiao.analysis.mapper.UserMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * @author baidu
// * @date 2018/6/19 下午4:02
// * @description ${TODO}
// **/
//@Component
//public class UserCredentials implements UserDetailsService {
//    private static final Logger logger = LoggerFactory.getLogger(UserCredentials.class);
//
//    private static final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.fly.caipiao.analysis.entity.User  entity = userMapper.getByUsername(username);
//        if(entity == null){
//            return null;
//        }
//        User user = new User(username, entity.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//        return user;
//    }
//}
