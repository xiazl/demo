package com.fly.caipiao.analysis.web.controller.vo;

/**
 * @author baidu
 * @date 2018/6/24 下午10:07
 * @description 文件属性
 **/
public class FileVO {

    private String name;
    private Long size;

    public FileVO() {
    }

    public FileVO(String name, Long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
