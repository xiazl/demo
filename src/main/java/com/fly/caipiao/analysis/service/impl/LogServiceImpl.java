package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.service.LogMongoService;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.ResourceVisitVO;
import com.fly.caipiao.analysis.vo.VisitVO;
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

@Service("logService")
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private LogMongoService logMongoService;
    @Autowired
    private DataLogMapper dataLogMapper;

    @Override
    public PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<VisitVO> listByPv(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.listByPv(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<VisitVO> listByUv(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.listByUv(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.listByDate(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<ResourceVisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.listByResource(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.listByPlatform(conditionVO);
        return PageHelp.getDataResult(list);
    }
}
