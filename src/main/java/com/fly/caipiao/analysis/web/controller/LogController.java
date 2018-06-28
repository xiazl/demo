package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping("/listByPlatAndDate")
    public Result listByPlatAndDate() {
        List<StatisticsVO> list = logService.listByPlatAndDate();
        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : list){
            map.put(vo.getTime()+vo.getName(),vo.getCount());
        }

        List<String> ykeys = logService.listYKeys();
        List<String> xkeys = logService.listXKeys();

        RaphaelChartVO chartVO = new RaphaelChartVO();
        List<Map> mapList = new ArrayList<>();
        for(String x : xkeys){
            Map<String,Object> nmap = new HashMap<>();
            for(String y : ykeys) {
                if(map.containsKey(x+y)){
                    nmap.put(y, map.get(x+y));
                } else {
                    nmap.put(y, 0);
                }
            }
            nmap.put("time",x);
            mapList.add(nmap);
        }

        chartVO.setData(mapList);
        chartVO.setXkey("time");
        chartVO.setLabels(ykeys);
        chartVO.setYkeys(ykeys);
        return ResponseData.success(chartVO);
    }

    @ResponseBody
    @RequestMapping("/listByPlatAndMonth")
    public Result listByPlatAndMonth() {
        List<StatisticsVO> list = logService.listByPlatAndMonth();
        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : list){
            map.put(vo.getTime()+vo.getName(),vo.getCount());
        }

        List<String> ykeys = logService.listYKeys();
        List<String> xkeys = logService.listXKeys();

        RaphaelChartVO chartVO = new RaphaelChartVO();
        List<Map> mapList = new ArrayList<>();
        for(String x : xkeys){
            Map<String,Object> nmap = new HashMap<>();
            for(String y : ykeys) {
                if(map.containsKey(x+y)){
                    nmap.put(y, map.get(x+y));
                } else {
                    nmap.put(y, 0);
                }
            }
            mapList.add(nmap);
        }


        chartVO.setData(mapList);
        chartVO.setXkey("time");
        chartVO.setLabels(ykeys);
        chartVO.setYkeys(ykeys);
        return ResponseData.success(chartVO);
    }

}
