package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.entity.HbaseEntity;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2018/7/12 下午3:00
 * @description ${TODO}
 **/
public interface HbaseService {

    /**
     * 从hbase查询原数据
     * @param pageBean 分页参数
     * @param lastRowKey key值
     * @return
     */
    PageDataResult<HbaseEntity> list(PageBean pageBean, String lastRowKey);
    /**
     * 批量写入
     */
    void insertBatch(List<CDNLogEntity> list);

    /**
     * 按天统计数据写入mongo
     * @param time
     */
    void aggregationStatistics(Long time);
    }
