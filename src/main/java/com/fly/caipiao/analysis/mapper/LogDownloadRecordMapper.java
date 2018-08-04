package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.LogDownloadRecord;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午10:30
 * @description 日志下载记录
 **/

@Mapper
public interface LogDownloadRecordMapper {

    /**
     * 查询文件是否存在
     * @param key
     * @return
     */
    Integer queryByNameKey(@Param("key") String key);

    /**
     * 更新状态
     * @param key
     * @return
     */
    Integer updateByKey(@Param("key") String key);

    /**
     * 通过key查询
     * @param domainLogDetail
     * @return
     */
    List<String> queryByKeys(List<CDNFileVO> domainLogDetail);

    /**
     * 插入
     * @param file
     * @return
     */
    Integer insert(LogDownloadRecord file);

    /**
     * 列表查询
     * @param conditionVO
     * @return
     */
    List<LogDownloadRecord> list(ConditionVO conditionVO);

    /**
     * 批量更新
     */
    void updateBatch(List<LogDownloadRecord> list);
}
