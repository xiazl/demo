package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.ErrorRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 用户信息表mapper
 **/

@Mapper
public interface ErrorRecordMapper {
    /**
     * 插入用户
     * @param record
     * @return
     */
    Integer insert(ErrorRecord record);
}
