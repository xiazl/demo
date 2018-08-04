package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.CDNSetting;
import com.fly.caipiao.analysis.entity.User;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/7/31 下午2:45
 * @description CDN域名设置mapper
 **/

@Mapper
public interface CDNSettingMapper {
    /**
     * 新增配置
     * @param setting
     * @return
     */
    Integer insert(CDNSetting setting);

    /**
     * 更新配置
     * @param setting
     * @return
     */
    Integer update(CDNSetting setting);

    /**
     * 删除配置
     * @param id
     * @return
     */
    Integer updateStatus(Integer id);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<CDNSetting> list(ConditionVO conditionVO);

    /**
     * 检查域名
     * @param domain
     * @return
     */
    Integer checkDomain(@Param("domain") String domain);

    /**
     * 查询域名
     * @param domain
     * @return
     */
    User getByDomain(@Param("domain") String domain);

    /**
     * 查询域名
     * @param id
     * @return
     */
    CDNSetting getById(@Param("id") Integer id);

    /**
     * 域名查询
     * @return
     */
    List<String> listDomain();
}
