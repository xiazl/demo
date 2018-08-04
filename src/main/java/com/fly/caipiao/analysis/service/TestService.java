package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午4:09
 * @description ${TODO}
 **/
public interface TestService {
    /**
     * 查询
     * @return
     */
    @Deprecated
    List<CDNLogEntity> find();

    /**
     * 查询
     * @param query
     * @return
     */
    @Deprecated
    CDNLogEntity findOne(Query query);

    /**
     * 单个插入
     * @param userEntity
     */
    @Deprecated
    void insert(CDNLogEntity userEntity);

    /**
     * 批量插入
     * @param entities
     */
    @Deprecated
    void insertBatch(List<CDNLogEntity> entities, List<String> ids, Long timeMillis);

    /**
     * 清空集合数据
     */
    @Deprecated
    void clear();

    /**
     * 数据处理
     */
    @Deprecated
    void repair();
}
