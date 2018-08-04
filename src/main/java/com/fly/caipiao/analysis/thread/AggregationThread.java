package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.service.HbaseService;

/**
 * @author baidu
 * @date 2018/8/3 下午3:46
 * @description ${TODO}
 **/
public class AggregationThread implements Runnable {
    private HbaseService hbaseService;
    private Long time;

    public AggregationThread(HbaseService hbaseService, Long time) {
        this.hbaseService = hbaseService;
        this.time = time;
    }

    @Override
    public void run() {
        hbaseService.aggregationStatistics(time);
    }
}
