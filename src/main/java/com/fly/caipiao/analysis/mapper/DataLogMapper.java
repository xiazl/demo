package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;
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
    List<VisitVO> listByResource(ConditionVO conditionVO);

    /**
     * 按来源平台列表查询
     * @param conditionVO
     * @return
     */
    List<VisitVO> listByPlatform(ConditionVO conditionVO);
    
    /**
     * 按天统计(最近30天)
     * @param
     * @return
     */
    List<StatisticsVO> listByPlatAndDate();

    /**
     * 按月统计
     * @param
     * @return
     */
    List<StatisticsVO> listByPlatAndMonth();

    /**
     * 图表显示key值
     * @param
     * @return
     */
    List<String> listYKeys();

}
