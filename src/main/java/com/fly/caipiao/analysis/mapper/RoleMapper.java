package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 角色
 **/

@Mapper
public interface RoleMapper {
    /**
     * 查询角色列表
     * @return
     */
    List<Role> list();

    /**
     * 用户id
     * @param id
     * @return 角色
     */
    List<String> getRoleStringByUserId(Integer id);

    /**
     * 通过ID查询角色
     * @param id
     * @return
     */
    Role getById(Integer id);

}
