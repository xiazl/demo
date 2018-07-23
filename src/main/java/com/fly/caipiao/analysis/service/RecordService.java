package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/
public interface RecordService {
    /**
     * 推广记录查询
     * @return
     */
    PageDataResult<Record> list(PageBean pageBean, ConditionVO conditionVO, Integer type);

}
