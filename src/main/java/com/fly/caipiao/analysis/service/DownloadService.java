package com.fly.caipiao.analysis.service;

import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;

/**
 * @author baidu
 * @date 2018/8/1 下午4:12
 * @description 日志文件下载
 **/
public interface DownloadService {

    /**
     * 从阿里云下载日志文件
     * @param download 日志请求路径、大小、文件名
     */
    void downloadFile(CDNFileVO download);

}
