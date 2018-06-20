package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.service.LogMongoService;
import com.fly.caipiao.analysis.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("logService")
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private LogMongoService logMongoService;

    @Override
    @TimeConsuming("数据解析并保存")
    public void analysis(String name) {
        long startTime = System.currentTimeMillis();
        String regexDate  = "([^\\[\\]]+)\\s";
        String regexIp =" ([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3} ";
        String regexReferer = "\"(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        String regexHttp = " (https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

        Pattern patternDate = Pattern.compile(regexDate),
                patternIp = Pattern.compile(regexIp),
                patternReferer = Pattern.compile(regexReferer),
                patternTarget = Pattern.compile(regexHttp);
        Matcher matcher;
        FileInputStream inputStream;
        Scanner sc = null;
        try {
            File file = ResourceUtils.getFile("classpath:"+name);
            inputStream = new FileInputStream(file.getPath());
            sc = new Scanner(inputStream, "UTF-8");
            String str;
            List<CDNLogEntity> list = new ArrayList<>();
            int i = 0;
            while (sc.hasNextLine()) {
//                if(i == BATCH_SIZE){
//                    logMongoService.insertBatch(list);
//                    i = 0;
//                    list = new ArrayList<>();
//                }
                str = sc.nextLine();
                CDNLogEntity entity = new CDNLogEntity();
                matcher = patternDate.matcher(str);
                if(matcher.find()){
                    entity.setDate(matcher.group());
                }
                matcher = patternIp.matcher(str);
                if(matcher.find()){
                    entity.setIp(matcher.group());
                }
                matcher = patternReferer.matcher(str);
                if(matcher.find()){
                    entity.setReferer(matcher.group().substring(1));
                }
                matcher = patternTarget.matcher(str);
                if(matcher.find()){
                    entity.setTargetUrl(matcher.group().substring(1));
                }
//                list.add(entity);
                i++;
            }
            if(i > 0){
//                logMongoService.insertBatch(list);
            }
        } catch (Exception e) {
            LOGGER.error("数据解析"+e.getMessage(),e);
        } finally {
            sc.close();
        }

        long endTime = System.currentTimeMillis();
        LOGGER.info("=============>>> 保存数据消耗时间 {} ms",endTime - startTime);
    }

    private void read(){

    }
}
