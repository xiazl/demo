package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface RecordMapper {
    /**
     * 插入记录
     * @param records
     * @return
     */
    Integer insert(List<Record> records);

    /**
     * 查询是否重复
     * @param list
     * @param type
     * @return
     */
    List<String> queryRepeat(@Param("list") Set<String> list, @Param("type") Integer type);

    /**
     * 推广记录列表查询
     * @param conditionVO
     * @return
     */
    List<Record> list(@Param("condition") ConditionVO conditionVO, @Param("type") Integer type);


}
