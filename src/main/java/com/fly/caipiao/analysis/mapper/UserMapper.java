package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.User;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface UserMapper {
    /**
     * 插入用户
     * @param user
     * @return
     */
    Integer insert(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer updateStatus(Integer id);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<User> list(ConditionVO conditionVO);

    /**
     * 查询用户
     * @param username
     * @return
     */
    Integer checkUsername(@Param("username") String username);

    /**
     * 查询用户
     * @param username
     * @return
     */
    User getByUsername(@Param("username") String username);

    /**
     * 查询用户
     * @param id
     * @return
     */
    User getById(@Param("id") Integer id);
}
