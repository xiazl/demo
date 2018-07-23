package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.configuration.datasource.DataSource;
import com.fly.caipiao.analysis.configuration.datasource.DataSourceType;
import com.fly.caipiao.analysis.entity.*;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.CDNLogsMapper;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.mapper.ErrorRecordMapper;
import com.fly.caipiao.analysis.service.PhoenixService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2018/7/12 下午3:00
 * @description ${TODO}
 **/

@Service("phoenixService")
public class PhoenixServiceImpl implements PhoenixService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoenixServiceImpl.class);
    private static final Integer MONTH_PERIOD = -1;

    @Autowired
    private DataLogMapper dataLogMapper;
    @Autowired
    private CDNLogsMapper logsMapper;
    @Autowired
    private ErrorRecordMapper recordMapper;

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<HbaseEntity> list(PageBean pageBean, String startRowKey) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.list(startRowKey);
        return PageHelp.getDataResult(list);
    }

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<PvStatistics> aggregationPv(PageBean pageBean, Long time) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.aggregationPv(time);
        return PageHelp.getDataResult(list);
    }

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<UvStatistics> aggregationUv(PageBean pageBean, Long time) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.aggregationUv(time);
        return PageHelp.getDataResult(list);
    }

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<PlatformStatistics> aggregationPlatform(PageBean pageBean, Long time) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.aggregationPlatform(time);
        return PageHelp.getDataResult(list);
    }

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<ResourceStatistics> aggregationResource(PageBean pageBean, Long time) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.aggregationResource(time);
        return PageHelp.getDataResult(list);
    }

    @Override
    @DataSource(value = DataSourceType.hbase_phoenix)
    public PageDataResult<ResourcePlatformStatistics> aggregationResourcePlatform(PageBean pageBean, Long time) {
        PageHelper.offsetPage(pageBean.getiDisplayStart().intValue(),pageBean.getiDisplayLength());
        List list = logsMapper.aggregationResourcePlatform(time);
        return PageHelp.getDataResult(list);
    }

    @Override
    public void saveErrorRecord(ErrorRecord record) {
        record.setCreateTime(new Date());
        recordMapper.insert(record);
    }

    /**
     * 在数据很多数，查询全量数据耗时多，这里是提供最近一个月的数据
     * @return
     */
    private Long getLongTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,MONTH_PERIOD);
        return calendar.getTimeInMillis();
    }

}
