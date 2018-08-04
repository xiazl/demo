package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.*;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

/**
 * @author baidu
 * @date 2018/7/12 下午3:00
 * @description ${TODO}
 **/
public interface PhoenixService {

    /**
     * 详请查询（最近30天）
     * @param pageBean
     * @param startRowKey
     * @return
     */
    @Deprecated
    PageDataResult<HbaseEntity> list(PageBean pageBean, String startRowKey);

    /**
     * PV按天统计
     * @param pageBean
     * @param time
     * @return
     */
    PageDataResult<PvStatistics> aggregationPv(PageBean pageBean, Long time);

    /**
     * UV按天统计
     * @param pageBean
     * @param time
     * @return
     */
    PageDataResult<UvStatistics> aggregationUv(PageBean pageBean, Long time);

    /**
     * 平台访问量按天统计
     * @param pageBean
     * @param time
     * @return
     */
    PageDataResult<PlatformStatistics> aggregationPlatform(PageBean pageBean, Long time);

    /**
     * 资源访问量按天统计
     * @param pageBean
     * @param time
     * @return
     */
    PageDataResult<ResourceStatistics> aggregationResource(PageBean pageBean, Long time);

    /**
     * 资源-平台详情按天统计
     * @param pageBean
     * @param time
     * @return
     */
    PageDataResult<ResourcePlatformStatistics> aggregationResourcePlatform(PageBean pageBean, Long time);

    /**
     * 记录计算失败的数据
     */
    void saveErrorRecord(ErrorRecord record);


}
