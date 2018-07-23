package com.fly.caipiao.analysis.configuration.hbase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author baidu
 * @date 2018/7/11 下午10:21
 * @description ${TODO}
 **/

@Component
@ConfigurationProperties(prefix = "spring.data.hbase")
public class HbaseProperties {
    private String quorum;
    private String rootDir;
    private String port;

    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}