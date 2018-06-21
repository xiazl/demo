package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.AdminUser;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.AdminUserMapper;
import com.fly.caipiao.analysis.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/21 下午9:32
 * @description 用户管理
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public void add(AdminUser adminUser) {
        if(adminUser.getId() != null){
            adminUserMapper.update(adminUser);
        }
        adminUserMapper.insert(adminUser);
    }

    @Override
    public AdminUser getById(Integer id) {
        return adminUserMapper.getById(id);
    }

    @Override
    public void delete(Integer id) {
        adminUserMapper.updateStatus(id);
    }

    @Override
    public PageDataResult<AdminUser> list(PageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getPageSize());
        List list = adminUserMapper.list(null);
        return PageHelp.getDataResult(list);
    }
}
