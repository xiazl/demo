package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description 日志统计
 **/

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @RequestMapping("/index")
    public String index() {
        return "log_analysis";
    }

    @RequestMapping("/analysis")
    @ResponseBody
    public Result analysis(String fileName){
        dataService.analysis(fileName);
        return ResponseData.success();
    }

    @RequestMapping("/analysisAll")
    @ResponseBody
    public Result analysis(){
        dataService.analysis();
        return ResponseData.success();
    }
}
