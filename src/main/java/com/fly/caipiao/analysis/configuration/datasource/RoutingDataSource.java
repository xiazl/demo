package com.fly.caipiao.analysis.configuration.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author baidu
 * @date 2018/7/12 下午8:10
 * @description ${TODO}
 **/

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
