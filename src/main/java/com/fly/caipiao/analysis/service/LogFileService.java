package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/
public interface LogFileService {

    /**
     * 日志文件解析记录查询
     * @return
     */
    PageDataResult<LogFile> list(PageBean pageBean, ConditionVO conditionVO);


    /**
     * 展示目录文件
     * @return
     */
    List<FileVO> listDirFiles();

}
