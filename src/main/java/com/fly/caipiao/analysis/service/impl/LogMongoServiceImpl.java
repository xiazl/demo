package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.service.LogMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author baidu
 * @date 2018/6/18 下午4:10
 * @description ${TODO}
 **/

@Service("logMongoService")
public class LogMongoServiceImpl implements LogMongoService {
    private static final String COLLECTION_NAME = "user";

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void insert(CDNLogEntity entity) {
        mongoTemplate.insert(entity);
    }

    @Override
    public void insertBatch(List<CDNLogEntity> entities,List<String> ids) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<CDNLogEntity> result  = mongoTemplate.find(query,CDNLogEntity.class,COLLECTION_NAME);
        if(result.size() > 0) {
            entities.removeAll(result);
        }
        // 去重复
        Set<CDNLogEntity> set = new HashSet<>(entities);

        mongoTemplate.insert(new ArrayList<>(set),CDNLogEntity.class);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection("user");
    }


}
