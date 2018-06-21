package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface AdminUserMapper {
    /**
     * 插入用户
     * @param adminUser
     * @return
     */
    Integer insert(AdminUser adminUser);

    /**
     * 更新用户
     * @param adminUser
     * @return
     */
    Integer update(AdminUser adminUser);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer updateStatus(Integer id);

    /**
     * 列表查询
     * @param adminUser
     * @return
     */
    List<AdminUser> list(AdminUser adminUser);

    /**
     * 查询用户
     * @param username
     * @return
     */
    AdminUser getByUsername(@Param("username") String username);

    /**
     * 查询用户
     * @param id
     * @return
     */
    AdminUser getById(@Param("id") Integer id);
}
