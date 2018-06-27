package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.enums.RecordTypeEnum;
import com.fly.caipiao.analysis.configuration.properties.PathProperties;
import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description 推广记录
 **/

@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private PathProperties pathProperties;

    @RequestMapping("/resource")
    public String index() {
        return "resource";
    }

    @RequestMapping("/platform")
    public String record() {
        return "platform";
    }

    @ResponseBody
    @RequestMapping("/listResource")
    public PageDataResult<Record> listResource(PageBean pageBean, ConditionVO conditionVO) {
        return recordService.list(pageBean,conditionVO,RecordTypeEnum.RESOURCE.getCode());
    }

    @ResponseBody
    @RequestMapping("/listPlatform")
    public PageDataResult<Record> listPlatform(PageBean pageBean, ConditionVO conditionVO) {
        return recordService.list(pageBean,conditionVO,RecordTypeEnum.PLATFORM.getCode());
    }
}
