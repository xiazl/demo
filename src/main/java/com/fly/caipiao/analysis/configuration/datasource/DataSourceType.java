package com.fly.caipiao.analysis.configuration.datasource;


/**
 * @author baidu
 * @date 2018/7/12 下午8:17
 * @description ${TODO}
 **/

public enum DataSourceType {
    read("read", "从库"),
    write("write", "主库"),
    hbase_phoenix("phoenix", "hbase库");

    private String type;
    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
