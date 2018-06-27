package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.VisitVO;
import com.fly.caipiao.analysis.vo.ResourceVisitVO;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description CDN 日志
 **/

@Mapper
public interface DataLogMapper {
    /**
     * 写入日志
     * @param logs
     * @return
     */
    Integer insert(List<DataLog> logs);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<DataLog> list(ConditionVO conditionVO);

    /**
     * PV列表查询
     * @param conditionVO
     * @return
     */
    List<VisitVO> listByPv(ConditionVO conditionVO);

    /**
     * UV列表查询
     * @param conditionVO
     * @return
     */
    List<VisitVO> listByUv(ConditionVO conditionVO);


    /**
     * 按日期统计列表查询
     * @param conditionVO
     * @return
     */
    List<DateVisitVO> listByDate(ConditionVO conditionVO);


    /**
     * 按资源列表查询
     * @param conditionVO
     * @return
     */
    List<ResourceVisitVO> listByResource(ConditionVO conditionVO);

    /**
     * 按来源平台列表查询
     * @param conditionVO
     * @return
     */
    List<VisitVO> listByPlatform(ConditionVO conditionVO);



}
