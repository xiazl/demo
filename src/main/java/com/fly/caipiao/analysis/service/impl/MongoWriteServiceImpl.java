package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.*;
import com.fly.caipiao.analysis.service.MongoWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author baidu
 * @date 2018/7/18 下午4:10
 * @description 数据写入
 **/

@Service("mongoWriteService")
public class MongoWriteServiceImpl implements MongoWriteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoWriteServiceImpl.class);
    private static final String COLLECTION_NAME = "user";
    private static final String PV_COLLECTION_NAME = "pv";
    private static final String UV_COLLECTION_NAME = "uv";
    private static final String PLATFORM_COLLECTION_NAME = "platform";
    private static final String RESOURCE_COLLECTION_NAME = "resource";
    private static final String RESOURCE_PLATFORM_COLLECTION_NAME = "resource_platform";


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
    public void insertBatchPv(List<PvStatistics> entities) {
        List<String> ids = new ArrayList<>();
        for(PvStatistics entity : entities){
            entity.setId();
            ids.add(entity.getId());
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<PvStatistics> result  = mongoTemplate.find(query,PvStatistics.class,PV_COLLECTION_NAME);
        List<String> oldIds = new ArrayList<>();
        Map<String,PvStatistics> map = new HashMap<>();
        for(PvStatistics cs : result){
            map.put(cs.getId(),cs);
        }

        for(PvStatistics entity : entities){
            if(map.containsKey(entity.getId())){
                oldIds.add(entity.getId());
                entity.setCount(entity.getCount()+map.get(entity.getId()).getCount());
            }
        }

        this.remove(oldIds,PvStatistics.class);

        mongoTemplate.insert(entities,PvStatistics.class);

    }

    @Override
    public void insertBatchUv(List<UvStatistics> entities) {
        List<String> ids = new ArrayList<>();
        for(UvStatistics entity : entities){
            entity.setId();
            ids.add(entity.getId());
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<UvStatistics> result  = mongoTemplate.find(query,UvStatistics.class,UV_COLLECTION_NAME);
        List<String> oldIds = new ArrayList<>();
        Map<String,UvStatistics> map = new HashMap<>();
        for(UvStatistics cs : result){
            map.put(cs.getId(),cs);
        }

        for(UvStatistics entity : entities){
            if(map.containsKey(entity.getId())){
                oldIds.add(entity.getId());
                entity.setCount(entity.getCount()+map.get(entity.getId()).getCount());
            }
        }

        this.remove(oldIds,UvStatistics.class);

        mongoTemplate.insert(entities,UvStatistics.class);

    }

    @Override
    public void insertBatchPlatform(List<PlatformStatistics> entities) {
        List<String> ids = new ArrayList<>();
        for(PlatformStatistics entity : entities){
            entity.setId();
            ids.add(entity.getId());
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<PlatformStatistics> result  = mongoTemplate.find(query,PlatformStatistics.class,PLATFORM_COLLECTION_NAME);
        List<String> oldIds = new ArrayList<>();
        Map<String,PlatformStatistics> map = new HashMap<>();
        for(PlatformStatistics cs : result){
            map.put(cs.getId(),cs);
        }

        for(PlatformStatistics entity : entities){
            if(map.containsKey(entity.getId())){
                oldIds.add(entity.getId());
                entity.setCount(entity.getCount()+map.get(entity.getId()).getCount());
            }
        }

        this.remove(oldIds,PlatformStatistics.class);
        mongoTemplate.insert(entities,PlatformStatistics.class);

    }

    @Override
    public void insertBatchResource(List<ResourceStatistics> entities) {
        List<String> ids = new ArrayList<>();
        for(ResourceStatistics entity : entities){
            entity.setId();
            ids.add(entity.getId());
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<ResourceStatistics> result  = mongoTemplate.find(query,ResourceStatistics.class,RESOURCE_COLLECTION_NAME);
        List<String> oldIds = new ArrayList<>();
        Map<String,ResourceStatistics> map = new HashMap<>();
        for(ResourceStatistics cs : result){
            map.put(cs.getId(),cs);
        }

        for(ResourceStatistics entity : entities){
            if(map.containsKey(entity.getId())){
                oldIds.add(entity.getId());
                entity.setCount(entity.getCount()+map.get(entity.getId()).getCount());
            }
        }

        this.remove(oldIds,ResourceStatistics.class);

        mongoTemplate.insert(entities,ResourceStatistics.class);

    }

    @Override
    public void insertBatchResourcePlatform(List<ResourcePlatformStatistics> entities) {
        List<String> ids = new ArrayList<>();
        for(ResourcePlatformStatistics entity : entities){
            entity.setId();
            ids.add(entity.getId());
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").in(ids);
        query.addCriteria(criteria);
        List<ResourcePlatformStatistics> result  = mongoTemplate.find(query,ResourcePlatformStatistics.class,RESOURCE_PLATFORM_COLLECTION_NAME);
        List<String> oldIds = new ArrayList<>();
        Map<String,ResourcePlatformStatistics> map = new HashMap<>();
        for(ResourcePlatformStatistics cs : result){
            map.put(cs.getId(),cs);
        }

        for(ResourcePlatformStatistics entity : entities){
            if(map.containsKey(entity.getId())){
                oldIds.add(entity.getId());
                entity.setCount(entity.getCount()+map.get(entity.getId()).getCount());
            }
        }

        this.remove(oldIds,ResourcePlatformStatistics.class);

        mongoTemplate.insert(entities,ResourcePlatformStatistics.class);
    }

    /**
     * 老数据清除
     * @param ids
     * @param entityClass
     */
    private void remove(List<String> ids,Class<?> entityClass){
        if(ids.size() > 0){
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("id").in(ids);
            query.addCriteria(criteria);
            mongoTemplate.remove(query,entityClass);
        }

//        try {
//            Thread.sleep(2000L);
//        } catch (InterruptedException e) {
//            LOGGER.error(e.getMessage(),e);
//        }
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection("user");
    }

}
