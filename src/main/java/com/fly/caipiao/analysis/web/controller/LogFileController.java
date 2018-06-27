package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.ApiResultVO;
import com.fly.caipiao.analysis.configuration.properties.PathProperties;
import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.DataService;
import com.fly.caipiao.analysis.service.LogFileService;
import com.fly.caipiao.analysis.service.LogService;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description 日志统计
 **/

@Controller
@RequestMapping("/logFile")
public class LogFileController {

    @Autowired
    private LogFileService logFileService;
    @Autowired
    private PathProperties pathProperties;
    @Autowired
    private LogService logService;
    @Autowired
    private DataService dataService;

    @RequestMapping("/index")
    public String index() {
        return "log_file";
    }

    @RequestMapping("/record")
    public String record() {
        return "log_analysis_record";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<LogFile> list(PageBean pageBean, ConditionVO conditionVO) {
        return logFileService.list(pageBean,conditionVO);
    }

    @RequestMapping("/analysis")
    @ResponseBody
    public ApiResultVO analysis(String fileName){
        dataService.analysis(fileName);
        return new ApiResultVO();
    }

    @ResponseBody
    @RequestMapping("/listDir")
    public Result listDir() {
        // 获取制定目录下的日后文件名
        String basePath = pathProperties.getPath();
        List<FileVO> list = new ArrayList<>();
        this.getFiles(list,basePath);

        return ResponseData.success(list);
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
