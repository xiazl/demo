
package com.fly.caipiao.analysis.common.utils;

import com.fly.caipiao.analysis.framework.excepiton.AppException;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * http 客户端工具类
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);


    public static String httpGet(String url) {

        long start = System.currentTimeMillis();

        LOGGER.info("====================> 调用开始，地址 {} ", url);

        HttpGet httpGet = new HttpGet(url);
        String respString = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(50000).build();

            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            respString = EntityUtils.toString(httpResponse.getEntity());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("====================> 返回信息，返回值 {} ", respString);
            }

        } catch (IOException e) {
            LOGGER.error("Error", e);
            throw new AppException("HTTP GET请求失败", e);
        } finally {

            long end = System.currentTimeMillis();

            LOGGER.info("====================> 调用完毕，地址 {} ，消耗时间 {} ms", url, end - start);
        }

        return respString;
    }


}
