package com.fly.caipiao.analysis.service;

/**
 * @author baidu
 * @date 2018/7/8 下午3:52
 * @description spark
 **/
public interface SparkService {

    /**
     * 数据加载
     */
    void load();

    /**
     * 数据写入
     */
    void save();
}
