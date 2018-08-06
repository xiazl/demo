package com.fly.caipiao.analysis.mapper;

import com.fly.caipiao.analysis.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2018/7/19 下午10:30
 * @description 按天统计写入到mongo
 **/

@Mapper
public interface CDNLogsMapper {
    /**
     * 日志详情查询
     * @param startRowKey
     * @return
     */
    @Deprecated
    List<CDNLogEntity> list(@Param("rowKey") String startRowKey);

    /**
     * Pv按天统计
     * @param time
     * @return
     */
    List<PvStatistics> aggregationPv(@Param("time") Long time);

    /**
     * Uv按天统计
     * @param time
     * @return
     */
    List<UvStatistics> aggregationUv(@Param("time") Long time);

    /**
     * 平台访问量按天统计
     * @param time
     * @return
     */
    List<PlatformStatistics> aggregationPlatform(@Param("time") Long time);

    /**
     * 资源访问量按天统计
     * @param time
     * @return
     */
    List<ResourceStatistics> aggregationResource(@Param("time") Long time);

    /**
     * 资源-平台详情按天统计
     * @param time
     * @return
     */
    List<ResourcePlatformStatistics> aggregationResourcePlatform(@Param("time") Long time);

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer batchInsert(@Param("list") List<CDNLogEntity> list, @Param("time") Long time);
}
