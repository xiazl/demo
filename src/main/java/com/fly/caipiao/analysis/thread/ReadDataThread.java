package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.service.ReadDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author baidu
 * @date 2018/8/2 上午11:59
 * @description 日志文件数据读取
 **/
public class ReadDataThread implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReadDataThread.class);

    private String filePath;
    private ReadDataService readDataService;

    public ReadDataThread(String filePath, ReadDataService readDataService) {
        this.filePath = filePath;
        this.readDataService = readDataService;
    }

    @Override
    public void run() {
        try {
            readDataService.readData(filePath);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
