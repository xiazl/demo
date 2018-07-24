package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.ResourcePlatformStatistics;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.service.MongoReadService;
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
 * @date 2018/7/19 下午2:58
 * @description ${TODO}
 **/

@Service("mongoReadService")
public class MongoReadServiceImpl implements MongoReadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoReadServiceImpl.class);
    private static final String PV_COLLECTION_NAME = "pv";
    private static final String UV_COLLECTION_NAME = "uv";
    private static final String PLATFORM_COLLECTION_NAME = "platform";
    private static final String RESOURCE_COLLECTION_NAME = "resource";
    private static final String RESOURCE_PLATFORM_COLLECTION_NAME = "resource_platform";

    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataLogMapper dataLogMapper;

    @Override
    public PageDataResult<ResourcePlatformStatistics> list(PageBean pageBean, ConditionVO conditionVO) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.orOperator(Criteria.where("referer").is(conditionVO.getsSearch()),
                    Criteria.where("targetUrl").regex(conditionVO.getsSearch()));
        }
        query.addCriteria(criteria);

        long size = mongoTemplate.count(query,ResourcePlatformStatistics.class,RESOURCE_PLATFORM_COLLECTION_NAME);

        query.with(new Sort(Sort.Direction.DESC,"count"));

        query.skip(pageBean.getiDisplayStart());
        query.limit(pageBean.getiDisplayLength());
        List<ResourcePlatformStatistics> list = mongoTemplate.find(query,ResourcePlatformStatistics.class,RESOURCE_PLATFORM_COLLECTION_NAME);
        return PageHelp.getDataResult(list,size);
    }

    @Override
    public PageDataResult<VisitVO> listByPv(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"targetUrl");
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(Aggregation
                        .newAggregationOptions().allowDiskUse(true).build()), PV_COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, PV_COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByUv(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"targetUrl");
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("targetUrl","count").and("name").previousOperation();
        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation)
                .withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build()),
                UV_COLLECTION_NAME, VisitVO.class).getMappedResults().size();

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation,sortOperation,
                skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, UV_COLLECTION_NAME, VisitVO.class);


        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"date");
        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("date").sum("count").as("count");
        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation,
                groupOperation), UV_COLLECTION_NAME,DateVisitVO.class).getMappedResults().size();

        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());
        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        ProjectionOperation resultOperation = Aggregation.project("count").and("date").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,
                resultOperation,sortOperation,skipOperation,limitOperation);

        AggregationResults<DateVisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, UV_COLLECTION_NAME, DateVisitVO.class);
        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"targetUrl");

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(
                Aggregation.newAggregationOptions().allowDiskUse(true).build()),RESOURCE_COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, RESOURCE_COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByResourceDetail(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"referer");

        if(StringUtils.isNotBlank(conditionVO.getKeyword())) {
            criteria.and("targetUrl").is(conditionVO.getKeyword());
        }

        ProjectionOperation projectionOperation = Aggregation.project("count").and("_id").as("name");;

        GroupOperation groupOperation = Aggregation.group("referer").sum("count").as("count");

        return this.detailCommon(pageBean,criteria,projectionOperation,groupOperation);
    }

    private PageDataResult<VisitVO> detailCommon(PageBean pageBean,Criteria criteria,ProjectionOperation projectionOperation,GroupOperation groupOperation){
        MatchOperation matchOperation = Aggregation.match(criteria);

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation,groupOperation).withOptions(
                Aggregation.newAggregationOptions().allowDiskUse(true).build()), RESOURCE_PLATFORM_COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation, projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, RESOURCE_PLATFORM_COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"referer");

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("referer").sum("count").as("count");

        long size = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, groupOperation).withOptions(
                Aggregation.newAggregationOptions().allowDiskUse(true).build()), PLATFORM_COLLECTION_NAME,VisitVO.class)
                .getMappedResults().size();

        ProjectionOperation projectionOperation = Aggregation.project("count")
                .and("_id").as("name");

        SkipOperation skipOperation = Aggregation.skip(pageBean.getiDisplayStart());
        LimitOperation limitOperation = Aggregation.limit(pageBean.getiDisplayLength());

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation,
                sortOperation,skipOperation, limitOperation).withOptions(Aggregation.newAggregationOptions().
                allowDiskUse(true).build());

        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, PLATFORM_COLLECTION_NAME, VisitVO.class);

        return PageHelp.getDataResult(aggregationResults.getMappedResults(),size);
    }

    @Override
    public PageDataResult<VisitVO> listByPlatformDetail(PageBean pageBean, ConditionVO conditionVO) {
        Criteria criteria =this.getCriteria(conditionVO,"targetUrl");

        if(StringUtils.isNotBlank(conditionVO.getKeyword())) {
            criteria.and("referer").is(conditionVO.getKeyword());
        }

        ProjectionOperation projectionOperation = Aggregation.project("count").and("_id").as("name");;

        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");

        return this.detailCommon(pageBean,criteria,projectionOperation,groupOperation);
    }

    @Override
    public List<StatisticsVO> listByPlatAndDate(String startDate,String endDate){
        Criteria criteria = new Criteria();
        criteria.and("time").gte(startDate).lte(endDate);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("time","count")
                .andExpression("date").as("time")
                .and("targetUrl").as("name");
        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");


        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,groupOperation,matchOperation);
        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, UV_COLLECTION_NAME, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<StatisticsVO> listByDate(String startDate, String endDate,String collectionName) {
        Criteria criteria = new Criteria();
        criteria.and("date").gte(startDate).lte(endDate);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("date","count")
                .and("_id").as("time");

        GroupOperation groupOperation = Aggregation.group("date").sum("count").as("count");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation);
        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<StatisticsVO> listByPlatAndMonth(String startMonth,String endMonth){
        Criteria criteria = new Criteria();
        criteria.and("date").gte(startMonth).lte(endMonth);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("date","count")
                .andExpression("substr(date,0,7)").as("time")
                .and("_id").as("time");

        GroupOperation groupOperation = Aggregation.group("name","time").sum("count").as("count");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,matchOperation,groupOperation,sortOperation);
        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, UV_COLLECTION_NAME, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<StatisticsVO> listByMonth(String startMonth,String endMonth,String collectionName){
        Criteria criteria = new Criteria();
        criteria.and("month").gte(startMonth).lte(endMonth);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = Aggregation.project("date","count")
                .andExpression("substr(date,0,7)").as("month");
        GroupOperation groupOperation = Aggregation.group("month").sum("count").as("count");

        ProjectionOperation resultOperation = Aggregation.project("count").and("_id").as("time");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation,matchOperation,groupOperation,resultOperation);

        AggregationResults<StatisticsVO> aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, StatisticsVO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<String> listYKeys(){
        GroupOperation groupOperation = Aggregation.group("targetUrl").sum("count").as("count");
        ProjectionOperation projectionOperation = Aggregation.project("targetUrl").and("name").previousOperation();

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"count");

        Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation,sortOperation).withOptions(Aggregation
                .newAggregationOptions().allowDiskUse(true).build());
        AggregationResults<VisitVO> aggregationResults = mongoTemplate.aggregate(aggregation, UV_COLLECTION_NAME, VisitVO.class);
        List<String> list  = new ArrayList<>();
        for(VisitVO visit: aggregationResults){
            list.add(visit.getName());
        }

        return list;
    }

    /**
     * 构建查询条件
     * @param conditionVO
     * @param targetName
     * @return
     */
    private Criteria getCriteria(ConditionVO conditionVO,String targetName){
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(conditionVO.getsSearch())) {
            criteria.and(targetName).regex(conditionVO.getsSearch());
        }
        return criteria;
    }

}
