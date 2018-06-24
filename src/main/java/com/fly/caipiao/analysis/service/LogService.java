package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/
public interface LogService {
    void analysis(String name);

    /**
     * 日志查询
     * @return
     */
    PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO);
}
