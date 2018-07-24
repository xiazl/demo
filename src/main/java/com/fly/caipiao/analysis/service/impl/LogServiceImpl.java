package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("logService")
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
    private static final String COLLECTION_NAME = "user";
    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataLogMapper dataLogMapper;

    @Override
    public PageDataResult<CDNLogEntity> list(PageBean pageBean,ConditionVO conditionVO) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.orOperator(Criteria.where("referer").regex(conditionVO.getsSearch()),
                    Criteria.where("targetUrl").regex(conditionVO.getsSearch()));
        }
        query.addCriteria(criteria);

        long size = mongoTemplate.count(query,CDNLogEntity.class);

        query.skip(pageBean.getiDisplayStart());
        query.limit(pageBean.getiDisplayLength());
        List<CDNLogEntity> list = mongoTemplate.find(query,CDNLogEntity.class);
        return PageHelp.getDataResult(list,size);
    }

    private Criteria getCriteria(ConditionVO conditionVO,String targetName){
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.and(targetName).regex(conditionVO.getsSearch());
        }
        return criteria;
    }

    @Override
    public PageDataResult<VisitVO> listByPv(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"targetUrl");
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        GroupOperation groupOperation = Aggregation.group("targetUrl").count().as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(Aggregation
                        .newAggregationOptions().allowDiskUse(true).build()), COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByUv(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"referer");
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("targetUrl","ip");

        GroupOperation groupOperation = Aggregation.group("targetUrl","ip");


        ProjectionOperation projectionOperation1 = Aggregation.project("targetUrl","count").and("name").previousOperation();
        GroupOperation groupOperation1 = Aggregation.group("targetUrl").count().as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation,
                groupOperation1,projectionOperation1).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build()), COLLECTION_NAME, VisitVO.class).getMappedResults().size();

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation,
                groupOperation1,projectionOperation1,sortOperation,skipOperation, limitOperation).withOptions(
                        Aggregation.newAggregationOptions().allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, VisitVO.class);


        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"date");
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("substr(dateTime,0,10)").as("date");

        GroupOperation groupOperation = Aggregation.group("date").count().as("count");
        long size = mongoTemplate.aggregate(Aggregation.newAggregation(projectionOperation,matchOperation,
                groupOperation), COLLECTION_NAME,VisitVO.class).getMappedResults().size();

        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());
        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        ProjectionOperation resultOperation = Aggregation.project("count").and("date").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,matchOperation,groupOperation,
                resultOperation,sortOperation,skipOperation,limitOperation);

        AggregationResults<DateVisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, DateVisitVO.class);
        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.and("targetUrl").regex(conditionVO.getsSearch());
        }
        if(StringUtils.isNotBlank(conditionVO.getKeyword())) {
            criteria.and("referer").is(conditionVO.getKeyword());
        }
        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("targetUrl").count().as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(
                Aggregation.newAggregationOptions().allowDiskUse(true).build()),COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.and("referer").regex(conditionVO.getsSearch());
        }
        if(StringUtils.isNotBlank(conditionVO.getKeyword())) {
            criteria.and("targetUrl").is(conditionVO.getKeyword());
        }
        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("referer").count().as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(
                Aggregation.newAggregationOptions().allowDiskUse(true).build()), COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public List<StatisticsVO> listByPlatAndDate(String startDate,String endDate){
        Criteria criteria = new Criteria();
        criteria.and("time").gte(startDate).lte(endDate);
        MatchOperation matchOperation = Aggregation.match(criteria);

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        ProjectionOperation projectionOperation = Aggregation.project("time","count")
                .andExpression("substr(dateTime,0,10)").as("time")
                .and("targetUrl").as("name");

        GroupOperation groupOperation = Aggregation.group("name","time").count().as("count");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,matchOperation,groupOperation,sortOperation);
        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<StatisticsVO> listByPlatAndMonth(String startMonth,String endMonth){
        Criteria criteria = new Criteria();
        criteria.and("time").gte(startMonth).lte(endMonth);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("time","count")
                .andExpression("substr(dateTime,0,7)").as("time")
                .and("targetUrl").as("name");

        GroupOperation groupOperation = Aggregation.group("name","time").count().as("count");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,matchOperation,groupOperation,sortOperation);
        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<String> listYKeys(){
        GroupOperation groupOperation = Aggregation.group("targetUrl").count().as("count");
        ProjectionOperation projectionOperation = Aggregation.project("targetUrl").and("name").previousOperation();

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation,sortOperation).withOptions(Aggregation
                .newAggregationOptions().allowDiskUse(true).build());
        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, VisitVO.class);
        List<String> list  = new ArrayList<>();
        for(VisitVO visit: aggregationResults){
            list.add(visit.getName());
        }

        return list;
    }

}
