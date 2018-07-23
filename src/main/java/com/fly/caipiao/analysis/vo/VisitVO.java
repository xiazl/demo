package com.fly.caipiao.analysis.vo;

import java.io.Serializable;

/**
 * @author baidu
 * @date 2018/6/24 下午5:46
 * @description ${TODO}
 **/
public class VisitVO implements Serializable {
    private String name;
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
