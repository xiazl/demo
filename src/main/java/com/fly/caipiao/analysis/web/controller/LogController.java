package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.utils.DateUtils;
import com.fly.caipiao.analysis.entity.ResourcePlatformStatistics;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.HbaseService;
import com.fly.caipiao.analysis.service.MongoReadService;
import com.fly.caipiao.analysis.service.PhoenixService;
import com.fly.caipiao.analysis.vo.DateVisitVO;
import com.fly.caipiao.analysis.vo.EChartVO;
import com.fly.caipiao.analysis.vo.StatisticsVO;
import com.fly.caipiao.analysis.vo.VisitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private static final String PV_COLLECTION_NAME = "pv";
    private static final String UV_COLLECTION_NAME = "uv";
    private static final String[]ykeys = {"PV","UV"};

    @Autowired
    private MongoReadService mongoReadService;
    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private PhoenixService phoenixService;

    @RequestMapping("/index")
    public String index() {
        return "log/log_detail";
    }

    @RequestMapping("/indexPv")
    public String indexPv() {
        return "log/log_pv";
    }

    @RequestMapping("/indexUv")
    public String indexUv() {
        return "log/log_uv";
    }

    @RequestMapping("/indexDate")
    public String indexDate() {
        return "log/log_date";
    }

    @RequestMapping("/indexResource")
    public String indexResource() {
        return "log/log_resource";
    }

    @RequestMapping("/indexPlatform")
    public String indexPlatform()
    {
        return "log/log_platform";
    }

    @RequestMapping("/indexUser")
    public String indexUser() {
        return "map";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<ResourcePlatformStatistics> list(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.list(pageBean,conditionVO);
    }


    @ResponseBody
    @RequestMapping("/listPv")
    public PageDataResult<VisitVO> listPv(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByPv(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listUv")
    public PageDataResult<VisitVO> listUv(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByUv(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listDate")
    public PageDataResult<DateVisitVO> listByDate(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByDate(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listResource")
    public PageDataResult<VisitVO> listByResource(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByResource(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listResourceDetail")
    public PageDataResult<VisitVO> listResourceDetail(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByResourceDetail(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listPlatform")
    public PageDataResult<VisitVO> listByPlatform(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByPlatform(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listPlatformDetail")
    public PageDataResult<VisitVO> listPlatformDetail(PageBean pageBean, ConditionVO conditionVO) {
        return mongoReadService.listByPlatformDetail(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/listByPlatAndDate")
    public Result listByPlatAndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        List<String> xkeys = DateUtils.getListDay(calendar.getTime(),new Date());
        int length = xkeys.size();
        List<StatisticsVO> pvList = mongoReadService.listByDate(xkeys.get(0),xkeys.get(length-1),PV_COLLECTION_NAME);

        List<StatisticsVO> uvList = mongoReadService.listByDate(xkeys.get(0),xkeys.get(length-1),UV_COLLECTION_NAME);



        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : pvList){
            map.put(vo.getTime() + ykeys[0], vo.getCount());
        }

        for(StatisticsVO vo : uvList){
            map.put(vo.getTime() + ykeys[1], vo.getCount());
        }

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

        keys.addAll(Arrays.asList(ykeys));

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
        List<StatisticsVO> pvList = mongoReadService.listByMonth(xkeys.get(0),xkeys.get(length-1),PV_COLLECTION_NAME);

        List<StatisticsVO> uvList = mongoReadService.listByMonth(xkeys.get(0),xkeys.get(length-1),UV_COLLECTION_NAME);

        Map<String,Object> map = new HashMap<>();
        for(StatisticsVO vo : pvList){
            map.put(vo.getTime() + ykeys[0], vo.getCount());
        }

        for(StatisticsVO vo : uvList){
            map.put(vo.getTime() + ykeys[1], vo.getCount());
        }

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
        keys.addAll(Arrays.asList(ykeys));

        chartVO.setData(mapList);
        chartVO.setKeys(keys);
        return ResponseData.success(chartVO);
    }

}
