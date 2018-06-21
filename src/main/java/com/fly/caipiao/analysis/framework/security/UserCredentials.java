package com.fly.caipiao.analysis.framework.security;

import com.fly.caipiao.analysis.entity.AdminUser;
import com.fly.caipiao.analysis.mapper.AdminUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author baidu
 * @date 2018/6/19 下午4:02
 * @description ${TODO}
 **/
@Component
public class UserCredentials implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserCredentials.class);

    private static final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser entity = adminUserMapper.getByUsername(username);
        if(entity == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        User user = new User(username, entity.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
