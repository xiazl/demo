package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.entity.LogDownloadRecord;
import com.fly.caipiao.analysis.entity.LogImportRecord;
import com.fly.caipiao.analysis.framework.page.CDNDownloadConditionVO;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
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
    PageDataResult<LogImportRecord> list(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 在线日志文件浏览
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<CDNFileVO> listOnline(PageBean pageBean, CDNDownloadConditionVO conditionVO);


    /**
     * 展示目录文件
     * @return
     */
    List<FileVO> listDirFiles();

    /**
     * 批量下载
     * @param list
     */
    void downloadFileBatch(List<CDNFileVO> list);

    /**
     * 全量下载
     * @param conditionVO 查询条件
     */
    void downloadFileAll(CDNDownloadConditionVO conditionVO);

    /**
     * 分页查询日志文件下载记录
     * @param pageBean
     * @param conditionVO
     * @return
     */
    PageDataResult<LogDownloadRecord> downRecord(PageBean pageBean, ConditionVO conditionVO);

    /**
     * 重现下载失败的文件
     */
    void reDownload();

    /**
     * 清除不再使用的日志文件
     */
    void clearFiles();

}
