package com.fly.caipiao.analysis.configuration.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author baidu
 * @date 2018/7/12 下午8:29
 * @description ${TODO}
 **/

@Configuration
@EnableTransactionManagement
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
public class DataSourceConfiguration {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean({"writeDataSourceProperties", "properties"})
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("readDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("phoenixDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.phoenix")
    public DataSourceProperties phoenixDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("writeDataSource")
    public DataSource writeDataSource(
            @Qualifier("writeDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(dataSourceType).build();
    }

    @Bean("readDataSource")
    public DataSource readDataSource(
            @Qualifier("readDataSourceProperties")
                    DataSourceProperties readDataSourceProperties) {
        return readDataSourceProperties.initializeDataSourceBuilder().type(dataSourceType).build();
    }

    @Bean("phoenixDataSource")
    public DataSource phoenixDataSource(
            @Qualifier("phoenixDataSourceProperties")
                    DataSourceProperties phoenixDataSourceProperties) {
        return phoenixDataSourceProperties.initializeDataSourceBuilder().type(dataSourceType).build();
    }

    @Bean("routingDataSource")
    public DataSource dataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource,
            @Qualifier("phoenixDataSource") DataSource phoenixDataSource) {
        RoutingDataSource proxy = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
        targetDataSources.put(DataSourceType.read.getType(), readDataSource);
        targetDataSources.put(DataSourceType.hbase_phoenix.getType(), phoenixDataSource);
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers(@Qualifier("routingDataSource") DataSource dataSource) {
        logger.info("-------------------- transactionManager init ---------------------");
        return new DataSourceTransactionManager(dataSource);
    }

}