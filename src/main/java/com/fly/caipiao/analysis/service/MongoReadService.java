package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.ResourcePlatformStatistics;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;

import java.util.List;

/**
 * @author baidu
 * @date 2018/7/19 下午2:55
 * @description ${TODO}
 **/
public interface MongoReadService {
    /**
     * 日志详情查询
     * @return
     */
    PageDataResult<ResourcePlatformStatistics> list(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按照PV统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByPv(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按照UV统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByUv(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按天统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按资源统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 资源详情统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByResourceDetail(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按来源平台统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 平台详情统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByPlatformDetail(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按天统计(最近30天)
     * @return
     */
    @Deprecated
    List<StatisticsVO> listByPlatAndDate(String startDate, String endDate);

    /**
     * 按天统计(最近30天)
     * @return
     */
    List<StatisticsVO> listByDate(String startDate, String endDate, String collectionName);

    /**
     * 按月统计(最近一年)
     * @return
     */
    @Deprecated
    List<StatisticsVO> listByPlatAndMonth(String startMonth, String endMonth);

    /**
     * 按月统计(最近一年)
     * @return
     */
    List<StatisticsVO> listByMonth(String startMonth, String endMonth, String collectionName);

    /**
     * 图表显示用
     * @return
     */
    List<String> listYKeys();

}
