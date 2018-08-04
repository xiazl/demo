package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.enums.RecordTypeEnum;
import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.entity.LogImportRecord;
import com.fly.caipiao.analysis.entity.Record;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.framework.page.ConditionVO;
import com.fly.caipiao.analysis.mapper.LogImportRecordMapper;
import com.fly.caipiao.analysis.mapper.RecordMapper;
import com.fly.caipiao.analysis.service.HbaseService;
import com.fly.caipiao.analysis.service.ReadDataService;
import org.apache.commons.lang3.time.DateFormatUtils;
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
 * @date 2018/8/2 下午1:54
 * @description 数据读取
 **/

@Service("readDataService")
public class ReadDataServiceImpl implements ReadDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadDataServiceImpl.class);
    private static final Integer BATCH_SIZE = 20000;

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private LogImportRecordMapper logImportRecordMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,timeout = 1800,rollbackFor = Exception.class)
    public void readData(String filePath) {
        this.saveLogFile(filePath);

        // 记录当前时间戳
        Long timeMillis = new Date().getTime();

        String regexDate = "([^\\[\\]]+\\])\\s";
//        String regexDate  = "([^\\[\\]]+)\\s";
        String regexIp = " ([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3} ";
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
            File file = ResourceUtils.getFile(filePath);
            inputStream = new FileInputStream(file.getPath());
            sc = new Scanner(inputStream, "UTF-8");
            String str;
            List<CDNLogEntity> list = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            Set<String> refererSet = new HashSet<>();
            Set<String> targetSet = new HashSet<>();
            String referer, target;
            int i = 0;
            while (sc.hasNextLine()) {
                if (i == BATCH_SIZE) {
//                    logMongoService.insertBatch(list,ids);
                    hbaseService.insertBatch(list, ids, timeMillis);

                    i = 0;
                    list = new ArrayList<>();
                    ids = new ArrayList<>();
                }
                str = sc.nextLine();
                CDNLogEntity entity = new CDNLogEntity();
                matcher = patternDate.matcher(str);
                if (matcher.find()) {
                    String time = matcher.group();
                    time = time.substring(0, time.length() - 2);
                    Date date = DateUtils.parseDate(time, Locale.US, "d/MMM/YYYY:H:m:s Z");
                    entity.setDateTime(DateFormatUtils.format(date, "yyyy-MM-dd HH:hh:ss",
                            TimeZone.getTimeZone("GMT+8"), Locale.US));
                }
                matcher = patternIp.matcher(str);
                if (matcher.find()) {
                    entity.setIp(matcher.group());
                }
                matcher = patternReferer.matcher(str);
                if (matcher.find()) {
                    referer = matcher.group().substring(1);
                    entity.setReferer(referer);
                    refererSet.add(referer);
                }
                matcher = patternTarget.matcher(str);
                if (matcher.find()) {
                    target = matcher.group().substring(1).split("\\?")[0];
                    entity.setTargetUrl(target);
                    targetSet.add(target);
                }

                entity.setId();
                ids.add(entity.getId());
                list.add(entity);
                i++;
            }
            sc.close();

            if (i > 0) {
//                logMongoService.insertBatch(list,ids);
                hbaseService.insertBatch(list, ids, timeMillis);

            }

            this.saveRecord(refererSet, targetSet);

            hbaseService.aggregationStatistics(timeMillis);

        } catch (FileNotFoundException e) {
            LOGGER.error("数据解析异常" + e.getMessage(), e);
            throw new AppException("未找到文件路径");
        } catch (ParseException e) {
            sc.close();
            LOGGER.error("数据解析异常" + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }

    }

    /**
     * 记录日志文件分析记录
     * @param name
     */
    private void saveLogFile(String name){
        List<LogImportRecord> record = logImportRecordMapper.list(new ConditionVO());
        LogImportRecord logFile = new LogImportRecord();
        logFile.setName(name);
        logFile.setKey(MD5Encrypt.getEncrypt().encode(name));
        logFile.setSize((int)new File(name).length());

        logImportRecordMapper.insert(logFile);
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
