package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.entity.LogDownloadRecord;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.mapper.LogDownloadRecordMapper;
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
    private static final String PV_COLLECTION_NAME = "pv";
    private static final String UV_COLLECTION_NAME = "uv";
    private static final String PLATFORM_COLLECTION_NAME = "platform";
    private static final String RESOURCE_COLLECTION_NAME = "resource";
    private static final String RESOURCE_PLATFORM_COLLECTION_NAME = "resource_platform";
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HbaseService hbaseService;

    @Autowired
    private LogDownloadRecordMapper recordMapper;

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
    public void insertBatch(List<CDNLogEntity> entities,List<String> ids,Long timeMillis) {
        hbaseService.insertBatchByPhoenix(entities,ids,timeMillis);
//        hbaseService.insertBatch(entities,ids,timeMillis);
//        mongoTemplate.insert(entities,CDNLogEntity.class);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection(COLLECTION_NAME);
        mongoTemplate.dropCollection(PV_COLLECTION_NAME);
        mongoTemplate.dropCollection(UV_COLLECTION_NAME);
        mongoTemplate.dropCollection(PLATFORM_COLLECTION_NAME);
        mongoTemplate.dropCollection(RESOURCE_COLLECTION_NAME);
        mongoTemplate.dropCollection(RESOURCE_PLATFORM_COLLECTION_NAME);

    }

    @Override
    public void repair() {
        List<LogDownloadRecord> list = recordMapper.list(new ConditionVO());
        for (LogDownloadRecord record : list){
            record.setKey(MD5Encrypt.getEncrypt().encode(record.getName()));
        }

        recordMapper.updateBatch(list);
    }


}
