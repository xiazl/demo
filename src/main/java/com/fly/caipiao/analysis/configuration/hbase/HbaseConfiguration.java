package com.fly.caipiao.analysis.configuration.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author baidu
 * @date 2018/7/12 下午8:03
 * @description ${TODO}
 **/

@org.springframework.context.annotation.Configuration
@AutoConfigureBefore({HbaseProperties.class})
public class HbaseConfiguration {

    @Autowired
    private HbaseProperties properties;

    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseConfiguration.class);

    @Bean("hbaseConnection")
    public Connection getConnection() {

        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", properties.getQuorum());

        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return connection;
    }
}
