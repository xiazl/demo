package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.LogImportRecord;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 日志导入记录
 **/

@Mapper
public interface LogImportRecordMapper {

    /**
     * 查询文件是否存在
     * @param key
     * @return
     */
    Integer queryByNameKey(@Param("key") String key);
    /**
     * 插入
     * @param file
     * @return
     */
    Integer insert(LogImportRecord file);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<LogImportRecord> list(ConditionVO conditionVO);

    /**
     * 查询文件名key
     * @param list
     * @return
     */
    List<String> listNameKeys(List<FileVO> list);
}
