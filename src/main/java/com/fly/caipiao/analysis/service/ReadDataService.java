package com.fly.caipiao.analysis.service;

/**
 * @author baidu
 * @date 2018/8/2 下午1:52
 * @description 数据读取
 **/
public interface ReadDataService {

    /**
     * 从文件读取数据,存入hbase并计算，最终数据写入到mongo
     * @param filePath
     */
    void readData(String filePath);
}
