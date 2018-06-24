package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.DataLog;
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
     * 插入用户
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


}
