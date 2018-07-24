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
    List<CDNLogEntity> find();

    /**
     * 查询
     * @param query
     * @return
     */
    CDNLogEntity findOne(Query query);

    /**
     * 单个插入
     * @param userEntity
     */
    void insert(CDNLogEntity userEntity);

    /**
     * 批量插入
     * @param entities
     */
    void insertBatch(List<CDNLogEntity> entities, List<String> ids, Long timeMillis);

    /**
     * 清空集合数据
     */
    @Deprecated
    void clear();
}
