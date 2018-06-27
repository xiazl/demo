package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.ResourceVisitVO;
import com.fly.caipiao.analysis.vo.VisitVO;
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

    @RequestMapping("/indexPv")
    public String indexPv() {
        return "log_pv";
    }

    @RequestMapping("/indexUv")
    public String indexUv() {
        return "log_uv";
    }

    @RequestMapping("/indexDate")
    public String indexDate() {
        return "log_date";
    }

    @RequestMapping("/indexResource")
    public String indexResource() {
        return "log_resource";
    }

    @RequestMapping("/indexPlatform")
    public String indexPlatform() {
        return "log_platform";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO) {
        return logService.list(pageBean,conditionVO);
    }


    @ResponseBody
    @RequestMapping("/listPv")
    public PageDataResult<VisitVO> listPv(PageBean pageBean, ConditionVO conditionVO) {
        return logService.listByPv(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listUv")
    public PageDataResult<VisitVO> listUv(PageBean pageBean, ConditionVO conditionVO) {
        return logService.listByUv(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listDate")
    public PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO) {
        return logService.listByDate(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listResource")
    public PageDataResult<ResourceVisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
        return logService.listByResource(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listPlatform")
    public PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO) {
        return logService.listByPlatform(pageBean,conditionVO);
    }
}
