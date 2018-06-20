package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.ApiResultVO;
import com.fly.caipiao.analysis.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("/analysis")
    public ApiResultVO analysis(String fileName){
        logService.analysis(fileName);
        return new ApiResultVO();
    }
}
