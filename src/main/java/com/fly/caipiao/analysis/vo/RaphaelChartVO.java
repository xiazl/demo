package com.fly.caipiao.analysis.vo;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/27 下午8:45
 * @description ${TODO}
 **/
public class RaphaelChartVO<T> {
    private List<String> xkeys;
    private List<String> ykeys;
    private List<String> labels;
    private List<T> data;

    public List<String> getXkeys() {
        return xkeys;
    }

    public void setXkeys(List<String> xkeys) {
        this.xkeys = xkeys;
    }

    public List<String> getYkeys() {
        return ykeys;
    }

    public void setYkeys(List<String> ykeys) {
        this.ykeys = ykeys;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
