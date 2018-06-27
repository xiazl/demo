package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.LogFileMapper;
import com.fly.caipiao.analysis.service.LogFileService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("logFileService")
public class LogFileServiceImpl implements LogFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFileServiceImpl.class);
    @Autowired
    private LogFileMapper logFileMapper;


    @Override
    public PageDataResult<LogFile> list(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = logFileMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

}
