package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.service.DownloadService;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author baidu
 * @date 2018/8/1 下午03:59
 * @description 日志文件下载
 **/
public class DownloadThread implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(DownloadThread.class);

    private CDNFileVO fileVO;
    private DownloadService downloadService;

    public DownloadThread(CDNFileVO fileVO, DownloadService downloadService) {
        this.fileVO = fileVO;
        this.downloadService = downloadService;
    }

    @Override
    public void run() {
        try {
            downloadService.downloadFile(fileVO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
