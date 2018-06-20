package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
