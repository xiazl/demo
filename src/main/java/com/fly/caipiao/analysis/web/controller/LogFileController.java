package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.common.ApiResultVO;
import com.fly.caipiao.analysis.common.utils.DateUtils;
import com.fly.caipiao.analysis.entity.LogImportRecord;
import com.fly.caipiao.analysis.framework.page.CDNDownloadConditionVO;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.CDNSettingService;
import com.fly.caipiao.analysis.service.DataService;
import com.fly.caipiao.analysis.service.DownloadService;
import com.fly.caipiao.analysis.service.LogFileService;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private DownloadService downloadService;
    @Autowired
    private DataService dataService;
    @Autowired
    private CDNSettingService settingService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss"), true));
    }

    @RequestMapping("/index")
    public String index() {
        return "log/log_file";
    }

    @RequestMapping("/record")
    public String record() {
        return "log/log_analysis_record";
    }

    @RequestMapping("/downloadRecord")
    public String downloadRecord() {
        return "log/log_down_record";
    }

    @RequestMapping("/logOnline")
    public String logDownload(Model model) {
        PageBean pageBean = new PageBean();
        pageBean.setiDisplayLength(0);
        List<String> domains = settingService.listDomains();
        model.addAttribute("domains",domains);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        model.addAttribute("startTime", DateUtils.format(calendar.getTime(),"yyyy-MM-dd"));

        return "log/log_online";
    }

    /**
     * 日志文件导入记录
     * @param pageBean
     * @param conditionVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageDataResult<LogImportRecord> list(PageBean pageBean, ConditionVO conditionVO) {
        return logFileService.list(pageBean,conditionVO);
    }

    /**
     * 阿里在线日志文件查看
     * @param pageBean
     * @param conditionVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/listOnline")
    public PageDataResult<CDNFileVO> listOnline(PageBean pageBean, CDNDownloadConditionVO conditionVO) {
        return logFileService.listOnline(pageBean,conditionVO);
    }

    /**
     * 单个文件导入
     * @param fileName
     * @return
     */
    @RequestMapping("/analysis")
    @ResponseBody
    public ApiResultVO analysis(String fileName){
        dataService.analysis(fileName);
        return new ApiResultVO();
    }

    /**
     * 服务器日志文件预览
     * @return
     */
    @ResponseBody
    @RequestMapping("/listDir")
    public Result listDir() {
        // 获取指定目录下的日志文件名
        List<FileVO> list = logFileService.listDirFiles();

        return ResponseData.success(list);
    }

    /**
     * 日志文件下载到服务器制定目录
     * @param download 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/download")
    public Result onlineDownload(CDNFileVO download)
    {
        List<CDNFileVO> list = new ArrayList<>();
        list.add(download);

        logFileService.downloadFileBatch(list);
        return ResponseData.success();
    }

    /**
     * 批量日志文件下载到服务器制定目录
     * @param list 参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadBatch",method = RequestMethod.POST)
    public Result onlineDownloadBatch(@RequestBody List<CDNFileVO> list)
    {
        logFileService.downloadFileBatch(list);
        return ResponseData.success();
    }

    /**
     * 全量日志文件下载到服务器制定目录
     * @param conditionVO 过滤参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadAll",method = RequestMethod.POST)
    public Result onlineDownloadAll(CDNDownloadConditionVO conditionVO)
    {
        logFileService.downloadFileAll(conditionVO);

        return ResponseData.success();
    }

    @ResponseBody
    @RequestMapping(value = "/reDownload")
    public Result reDownload()
    {
        logFileService.reDownload();

        return ResponseData.success();
    }

    /**
     * 查看文件记录
     * @param pageBean
     * @param conditionVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/downRecord")
    public PageDataResult downRecord(PageBean pageBean,ConditionVO conditionVO){
        return logFileService.downRecord(pageBean,conditionVO);
    }


    /**
     * 文件清理
     * @return
     */
    @RequestMapping("/clearFile")
    @ResponseBody
    public Result clearFiles(){
        logFileService.clearFiles();
        return ResponseData.success();
    }

}
