package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.User;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.web.controller.vo.UserPwdVO;
import com.fly.caipiao.analysis.web.controller.vo.UserVO;

/**
 * @author baidu
 * @date 2018/6/21 下午9:32
 * @description 用户管理
 **/
public interface UserService {
    /**
     * 用户添加
     */
    void add(UserVO user);

    /**
     * 用户更新
     * @param user
     */
    void update(User user);

    /**
     * 用户查询
     */
    User getById(Integer id);

    /**
     * 用户删除
     */
    void delete(Integer id);

    /**
     * 更新密码
     */
    void updatePwd(UserPwdVO userPwdVO);

    /**
     * 重置密码
     */
    void resetPwd(Integer id);

    /**
     * 用户查询
     * @return
     */
    PageDataResult<User> list(PageBean pageBean, ConditionVO conditionVO);

}
