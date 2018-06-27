package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.RecordMapper;
import com.fly.caipiao.analysis.service.RecordService;
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

@Service("recordService")
public class RecordServiceImpl implements RecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);
    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public PageDataResult<Record> list(PageBean pageBean, ConditionVO conditionVO,Integer type) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List<Record> list = recordMapper.list(conditionVO,type);
        return PageHelp.getDataResult(list);
    }

}
