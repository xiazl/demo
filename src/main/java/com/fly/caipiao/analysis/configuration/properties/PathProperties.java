package com.fly.caipiao.analysis.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author baidu
 * @date 2018/6/24 下午9:31
 * @description ${TODO}
 **/

@Component
@ConfigurationProperties(prefix = "log.data")
public class PathProperties {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
