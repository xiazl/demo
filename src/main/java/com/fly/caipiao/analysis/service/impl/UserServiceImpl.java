package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.RequestContext;
import com.fly.caipiao.analysis.entity.User;
import com.fly.caipiao.analysis.entity.UserRole;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.UserMapper;
import com.fly.caipiao.analysis.mapper.UserRoleMapper;
import com.fly.caipiao.analysis.service.UserService;
import com.fly.caipiao.analysis.web.controller.vo.UserPwdVO;
import com.fly.caipiao.analysis.web.controller.vo.UserVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2018/6/21 下午9:32
 * @description 用户管理
 **/

@Service
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_PWD = "123456a";
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void add(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        Integer roleId = userVO.getRole();
        if(user.getId() != null){
            userMapper.update(user);
        } else {
            Integer check = userMapper.checkUsername(user.getUsername());
            if(check != null) {
                throw new AppException("用户名已存在");
            }
            user.setCreateTime(new Date());
            user.setPassword(ENCODER.encode(DEFAULT_PWD));
            userMapper.insert(user);


            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void delete(Integer id) {
        userMapper.updateStatus(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updatePwd(UserPwdVO userPwdVO) {
        String username = RequestContext.getUser().getUsername();
        if(username == null){
            throw new AppException("用户不存在");
        }
        User entity = userMapper.getByUsername(username);
        if(!ENCODER.matches(userPwdVO.getOldPassword(),entity.getPassword())){
            throw new AppException("旧密码错误");
        }
        entity.setPassword(ENCODER.encode(userPwdVO.getPassword()));
        userMapper.update(entity);

    }

    @Override
    public void resetPwd(Integer id) {
    }

    @Override
    public PageDataResult<User> list(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = userMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }
}
