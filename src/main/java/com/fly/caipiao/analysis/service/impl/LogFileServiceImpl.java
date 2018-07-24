package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.configuration.properties.PathProperties;
import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.LogFileMapper;
import com.fly.caipiao.analysis.service.LogFileService;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("logFileService")
public class LogFileServiceImpl implements LogFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFileServiceImpl.class);
    @Autowired
    private LogFileMapper logFileMapper;
    @Autowired
    private PathProperties pathProperties;


    @Override
    public PageDataResult<LogFile> list(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = logFileMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public List<FileVO> listDirFiles() {
        // 获取制定目录下的日后文件名
        String basePath = pathProperties.getPath();
        List<FileVO> list = new ArrayList<>();
        this.getFiles(list,basePath);

        return list;
    }

    /**
     * 递归解析读取日志
     * @param list
     * @param path
     */
    private void getFiles(List<FileVO> list,String path){
        File dir = new File(path);
        String[] files = dir.list();
        String dirPath;
        for(String name : files){
            dirPath = path+File.separator + name;
            File file = new File(dirPath);
            if (file.isDirectory()){
                getFiles(list,dirPath);
            } else {
                FileVO fileVO = new FileVO(dirPath,file.length());
                list.add(fileVO);
            }
        }
    }

}
