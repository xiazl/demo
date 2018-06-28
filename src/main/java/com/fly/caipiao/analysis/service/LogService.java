package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.ResourceVisitVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/
public interface LogService {
    /**
     * 日志查询
     * @return
     */
    PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO);

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
    PageDataResult<ResourceVisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按来源平台统计
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 按天统计
     * @return
     */
    List<StatisticsVO> listByPlatAndDate();

    /**
     * 按月统计
     * @return
     */
    List<StatisticsVO> listByPlatAndMonth();

    /**
     * 图表显示用
     * @return
     */
    List<String> listYKeys();

    /**
     * 图表显示用
     * @return
     */
    List<String> listXKeys();
}
