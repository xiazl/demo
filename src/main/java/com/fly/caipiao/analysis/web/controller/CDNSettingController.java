package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.entity.CDNSetting;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.CDNSettingService;
import com.fly.caipiao.analysis.web.controller.vo.SettingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author baidu
 * @date 2018/7/31 下午11:45
 * @description CDN域名配置Controller
 **/

@Controller
@RequestMapping("/setting")
public class CDNSettingController {

    @Autowired
    private CDNSettingService settingService;

    @RequestMapping("/index")
    public String index() {
        return "setting";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result add(@RequestBody @Valid SettingVO settingVO) {
        CDNSetting setting = new CDNSetting();
        BeanUtils.copyProperties(settingVO,setting);

        settingService.add(setting);
        return ResponseData.success();
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<CDNSetting> list(PageBean pageBean, ConditionVO conditionVO) {
        // jquery datatable服务端分页
        return settingService.list(pageBean,conditionVO);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        settingService.delete(id);
        return ResponseData.success();
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result update(@RequestBody @Valid SettingVO settingVO) {
        CDNSetting setting = new CDNSetting();
        BeanUtils.copyProperties(settingVO,setting);
        settingService.update(setting);
        return ResponseData.success();
    }

}
