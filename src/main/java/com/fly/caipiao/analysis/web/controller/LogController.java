package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.utils.DateUtils;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.EChartVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    public String indexPlatform()
    {
        return "log_platform";
    }

    @RequestMapping("/resourceDetail")
    public String indexResource(Model model,String name) {
        model.addAttribute("name",name);
        return "log_resource_detail";
    }

    @RequestMapping("/platformDetail")
    public String indexPlatform(Model model,String name)
    {
        model.addAttribute("name",name);
        return "log_platform_detail";
    }

    @RequestMapping("/indexUser")
    public String indexUser() {
        return "map";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<CDNLogEntity> list(PageBean pageBean, ConditionVO conditionVO) {
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
    public PageDataResult<VisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        List<String> xkeys = DateUtils.getListDay(calendar.getTime(),new Date());
        int length = xkeys.size();
        List<StatisticsVO> list = logService.listByPlatAndDate(xkeys.get(0),xkeys.get(length-1));

        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : list){
            map.put(vo.getTime()+vo.getName(),vo.getCount());
        }

        List<String> ykeys = logService.listYKeys();

        EChartVO chartVO = new EChartVO();
        List<Map> mapList = new ArrayList<>();
        for(String x : xkeys){
            Map<String,Object> nmap = new HashMap<>();
            nmap.put("time",x);
            for(String y : ykeys) {
                if(map.containsKey(x+y)){
                    nmap.put(y, map.get(x+y));
                } else {
                    nmap.put(y, 0);
                }
            }
            mapList.add(nmap);
        }

        List<String> keys = new ArrayList<>();
        keys.add("time");
        keys.addAll(ykeys);

        chartVO.setData(mapList);
        chartVO.setKeys(keys);
        return ResponseData.success(chartVO);
    }

    @ResponseBody
    @RequestMapping("/listByPlatAndMonth")
    public Result listByPlatAndMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-1);
        List<String> xkeys = DateUtils.getListMonth(calendar.getTime(),new Date());
        int length = xkeys.size();
        List<StatisticsVO> list = logService.listByPlatAndMonth(xkeys.get(0),xkeys.get(length-1));
        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : list){
            map.put(vo.getTime()+vo.getName(),vo.getCount());
        }

        List<String> ykeys = logService.listYKeys();

        EChartVO chartVO = new EChartVO();
        List<Map> mapList = new ArrayList<>();
        for(String x : xkeys){
            Map<String,Object> nmap = new HashMap<>();
            nmap.put("time",x);
            for(String y : ykeys) {
                if(map.containsKey(x+y)){
                    nmap.put(y, map.get(x+y));
                } else {
                    nmap.put(y, 0);
                }
            }
            mapList.add(nmap);
        }

        List<String> keys = new ArrayList<>();
        keys.add("time");
        keys.addAll(ykeys);

        chartVO.setData(mapList);
        chartVO.setKeys(keys);
        return ResponseData.success(chartVO);
    }

}
