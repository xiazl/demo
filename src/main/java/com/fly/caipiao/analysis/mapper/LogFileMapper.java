package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface LogFileMapper {

    Integer queryByName(@Param("name") String name);
    /**
     * 插入
     * @param file
     * @return
     */
    Integer insert(LogFile file);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<LogFile> list(ConditionVO conditionVO);
}
