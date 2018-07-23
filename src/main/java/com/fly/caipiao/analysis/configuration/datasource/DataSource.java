package com.fly.caipiao.analysis.configuration.datasource;

import java.lang.annotation.*;


/**
 * @author baidu
 * @date 2018/7/12 下午8:03
 * @description ${TODO}
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    DataSourceType value() default DataSourceType.read;
}
