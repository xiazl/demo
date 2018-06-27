package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.enums.RecordTypeEnum;
import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.entity.DataLog;
import com.fly.caipiao.analysis.entity.LogFile;
import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.mapper.DataLogMapper;
import com.fly.caipiao.analysis.mapper.LogFileMapper;
import com.fly.caipiao.analysis.mapper.RecordMapper;
import com.fly.caipiao.analysis.service.DataService;
import com.fly.caipiao.analysis.service.LogMongoService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("dataService")
public class DataServiceImpl implements DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);
    private static final Integer BATCH_SIZE = 100000;

    @Autowired
    private LogMongoService logMongoService;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private DataLogMapper dataLogMapper;
    @Autowired
    private LogFileMapper logFileMapper;

    @Override
    @TimeConsuming("数据解析并保存")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void analysis(String name) {
        this.saveLogFile(name);

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
            File file = ResourceUtils.getFile(name);
            inputStream = new FileInputStream(file.getPath());
            sc = new Scanner(inputStream, "UTF-8");
            String str;
            List<DataLog> list = new ArrayList<>();
            Set<String> refererSet = new HashSet<>();
            Set<String> targerSet = new HashSet<>();
            String referer,target;
            int i = 0;
            while (sc.hasNextLine()) {
                if(i == BATCH_SIZE){
//                    logMongoService.insertBatch(list);
                    dataLogMapper.insert(list);
                    i = 0;
                    list = new ArrayList<>();
                }
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
                    referer  = matcher.group().substring(1);
                    entity.setReferer(referer);
                    refererSet.add(referer);
                }
                matcher = patternTarget.matcher(str);
                if(matcher.find()){
                    target = matcher.group().substring(1).split("\\?")[0];
                    entity.setTargetUrl(target);
                    targerSet.add(target);
                }
                list.add(entity);
                i++;
            }
            if(i > 0){
//                logMongoService.insertBatch(list);
                dataLogMapper.insert(list);
            }

            this.saveRecord(refererSet,targerSet);

        } catch (FileNotFoundException e) {
            LOGGER.error("数据解析异常"+e.getMessage(),e);
            throw new AppException("未找到文件路径");
        } catch (ParseException e) {
            LOGGER.error("数据解析异常"+e.getMessage(),e);
            throw new AppException(e.getMessage(),e);
        } finally {
            sc.close();
        }

    }

    /**
     * 记录日志文件分析记录
     * @param name
     */
    private void saveLogFile(String name){
        LogFile logFile = new LogFile();
        logFile.setName(name);
        logFile.setSize((int)new File(name).length());
        logFileMapper.insert(logFile);
    }

    /**
     * 保存日志关键信息
     */
    private void saveRecord(Set<String> refererSet,Set<String> targetSet){
        List<Record> list = new ArrayList<>();
        if(refererSet.size() > 0) {
            List<String> reList = recordMapper.queryRepeat(refererSet,RecordTypeEnum.PLATFORM.getCode());
            refererSet.removeAll(reList);
            for (String str : refererSet) {
                list.add(new Record(str, RecordTypeEnum.PLATFORM.getCode()));
            }
        }

        if(targetSet.size() > 0) {
            List<String> reList = recordMapper.queryRepeat(targetSet,RecordTypeEnum.RESOURCE.getCode());
            targetSet.removeAll(reList);
            for (String str : targetSet) {
                list.add(new Record(str, RecordTypeEnum.RESOURCE.getCode()));
            }
        }
        if(list.size() > 0) {
            recordMapper.insert(list);
        }
    }

}
