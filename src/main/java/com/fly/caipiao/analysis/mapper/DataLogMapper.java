package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface DataLogMapper {
    /**
     * 插入用户
     * @param adminUser
     * @return
     */
    Integer insert(List<AdminUser> adminUser);

    /**
     * 列表查询
     * @param adminUser
     * @return
     */
    List<AdminUser> list(AdminUser adminUser);


}
