package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.service.HbaseService;
import com.fly.caipiao.analysis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午4:10
 * @description ${TODO}
 **/

@Service("testService")
public class TestServiceImpl implements TestService {
    private static final String COLLECTION_NAME = "user";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HbaseService hbaseService;

    @Override
    public List find() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria);
        return mongoTemplate.find(query,CDNLogEntity.class);
    }

    @Override
    public CDNLogEntity findOne(Query query) {
        return mongoTemplate.findOne(query,CDNLogEntity.class);
    }


    @Override
    public void insert(CDNLogEntity entity) {
        mongoTemplate.insert(entity);
    }

    @Override
    public void insertBatch(List<CDNLogEntity> entities) {
        hbaseService.insertBatch(entities);
//        mongoTemplate.insert(entities,CDNLogEntity.class);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection(COLLECTION_NAME);
    }

}
