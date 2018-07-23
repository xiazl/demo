package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.*;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午4:09
 * @description ${TODO}
 **/
public interface MongoWriteService {

    /**
     * 单个插入
     * @param userEntity
     */
    void insert(CDNLogEntity userEntity);

    /**
     * 批量插入
     * @param entities
     */
    void insertBatch(List<CDNLogEntity> entities, List<String> ids);

    /**
     * 批量插入Pv统计
     * @param entities
     */
    void insertBatchPv(List<PvStatistics> entities);

    /**
     * 批量插入Uv统计
     * @param entities
     */
    void insertBatchUv(List<UvStatistics> entities);

    /**
     * 批量插入资源访问量统计
     * @param entities
     */
    void insertBatchPlatform(List<PlatformStatistics> entities);

    /**
     * 平台访问量统计
     * @param entities
     */
    void insertBatchResource(List<ResourceStatistics> entities);

    /**
     * 资源平台详情访问量
     * @param entities
     */
    void insertBatchResourcePlatform(List<ResourcePlatformStatistics> entities);

    /**
     * 清空集合数据
     */
    void clear();
}
