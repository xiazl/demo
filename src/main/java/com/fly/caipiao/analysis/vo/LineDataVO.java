package com.fly.caipiao.analysis.vo;

import java.util.List;

/**
 * @author baidu
 * @date 2018/6/27 下午8:45
 * @description echart 数据格式
 **/
public class LineDataVO {
    private String name;
    private String type = "line"; // 图表类型
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
