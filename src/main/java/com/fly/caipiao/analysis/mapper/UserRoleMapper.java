package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 角色
 **/

@Mapper
public interface UserRoleMapper {
    /**
     * 插入用户角色
     * @param userRole
     */
    void insert(UserRole userRole);
}
