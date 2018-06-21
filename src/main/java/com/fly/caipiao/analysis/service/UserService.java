package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.AdminUser;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

/**
 * @author baidu
 * @date 2018/6/21 下午9:32
 * @description 用户管理
 **/
public interface UserService {
    /**
     * 用户添加
     */
    void add(AdminUser adminUser);

    /**
     * 用户查询
     */
    AdminUser getById(Integer id);
    /**
     * 用户删除
     */
    void delete(Integer id);

    /**
     * 用户查询
     * @return
     */
    PageDataResult<AdminUser> list(PageBean pageBean);

}
