package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.CDNSetting;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2018/7/31 下午2:50
 * @description CDN域名管理
 **/
public interface CDNSettingService {
    /**
     * 添加配置
     */
    void add(CDNSetting setting);

    /**
     * 更新配置
     * @param setting
     */
    void update(CDNSetting setting);

    /**
     * 配置查询
     */
    CDNSetting getById(Integer id);

    /**
     * 配置删除
     */
    void delete(Integer id);

    /**
     * 列表查询
     * @return
     */
    PageDataResult<CDNSetting> list(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 域名查询
     * @return
     */
    List<String> listDomains();

}
