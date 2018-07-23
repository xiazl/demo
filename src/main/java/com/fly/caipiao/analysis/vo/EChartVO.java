package com.fly.caipiao.analysis.vo;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/27 下午8:45
 * @description ${TODO}
 **/
public class EChartVO<T> {
    private List<String> keys;
    private List<T> data;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
