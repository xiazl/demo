package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.framework.page.PageHelp;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.service.LogMongoService;
import com.fly.caipiao.analysis.service.LogService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    @Autowired
    private DataLogMapper dataLogMapper;

    @Override
    @TimeConsuming("数据解析并保存")
    public void analysis(String name) {
        String regexDate  = "([^\\[\\]]+\\])\\s";
//        String regexDate  = "([^\\[\\]]+)\\s";
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
            List<DataLog> list = new ArrayList<>();
            int i = 0;
            while (sc.hasNextLine()) {
//                if(i == BATCH_SIZE){
//                    logMongoService.insertBatch(list);
//                    i = 0;
//                    list = new ArrayList<>();
//                }
                str = sc.nextLine();
                DataLog entity = new DataLog();
                matcher = patternDate.matcher(str);
                if(matcher.find()){
                    String time = matcher.group();
                    time = time.substring(0,time.length()-2);
                    entity.setDateTime(DateUtils.parseDate(time,Locale.US,"d/MMM/YYYY:H:m:s Z"));
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
                list.add(entity);
                i++;
            }
            if(i > 0){
//                logMongoService.insertBatch(list);
                dataLogMapper.insert(list);
            }
        } catch (Exception e) {
            LOGGER.error("数据解析"+e.getMessage(),e);
        } finally {
            sc.close();
        }
        }

    @Override
    public PageDataResult<DataLog> list(PageBean pageBean,ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = dataLogMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }
}
