package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.ApiResultVO;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.service.LogMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/19 下午3:09
 * @description ${TODO}
 **/

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LogMongoService logMongoService;
    @RequestMapping("/list")
    public ApiResultVO find(){
        List<CDNLogEntity> list =  logMongoService.find();
        ApiResultVO resultVO = new ApiResultVO();
        resultVO.setData(list);
        return resultVO;
    }
}
