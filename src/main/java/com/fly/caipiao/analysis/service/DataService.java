package com.fly.caipiao.analysis.service;

/**
 * @author baidu
 * @date 2018/6/18 下午2:55
 * @description ${TODO}
 **/
public interface DataService {
    /**
     * 日志文件解析
     * @param name
     */
    void analysis(String name);

    /**
     * 日志文件解析（全量未处理文件）
     * @param
     */
    void analysis();

}
