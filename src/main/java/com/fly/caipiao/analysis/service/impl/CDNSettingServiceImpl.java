package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.entity.CDNSetting;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.CDNSettingMapper;
import com.fly.caipiao.analysis.service.CDNSettingService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2018/7/31 下午2:47
 * @description CDN域名管理
 **/

@Service("settingService")
public class CDNSettingServiceImpl implements CDNSettingService {
    private static final Integer DEFAULT_STATUS = 0;

    @Autowired
    private CDNSettingMapper settingMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void add(CDNSetting setting) {
        if(setting.getId() != null){
            settingMapper.update(setting);
        } else {
            Integer check = settingMapper.checkDomain(setting.getDomain());
            if(check != null) {
                throw new AppException("域名已存在");
            }
            setting.setCreateTime(new Date());
            setting.setStatusFlag(DEFAULT_STATUS);

            settingMapper.insert(setting);
        }
    }

    @Override
    public CDNSetting getById(Integer id) {
        return settingMapper.getById(id);
    }

    @Override
    public void delete(Integer id) {
        settingMapper.updateStatus(id);
    }

    @Override
    public void update(CDNSetting user) {
        settingMapper.update(user);
    }

    @Override
    public PageDataResult<CDNSetting> list(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = settingMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public List<String> listDomains() {
        return settingMapper.listDomain();
    }
}
