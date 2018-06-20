package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.mapper.UserMapper;
import com.fly.caipiao.analysis.service.LogMongoService;
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

@Service("logMongoService")
public class LogMongoServiceImpl implements LogMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List find() {
//        mongoTemplate.dropCollection("user");

        Query query = new Query();
        Criteria criteria = new Criteria();
//        criteria.and("referer").is("http://down.fcbtq.com");
        long size = mongoTemplate.count(query,CDNLogEntity.class);
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
        mongoTemplate.insert(entities,CDNLogEntity.class);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection("user");
    }


}
