package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.ApiResultVO;
import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("/index")
    public String index() {
        return "log";
    }

    @RequestMapping("/analysis")
    @ResponseBody
    public ApiResultVO analysis(String fileName){
        logService.analysis(fileName);
        return new ApiResultVO();
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO) {
        // jquery datatable服务端分页
        return logService.list(pageBean,conditionVO);
    }
}
