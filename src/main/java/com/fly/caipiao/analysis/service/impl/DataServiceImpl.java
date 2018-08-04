package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.mapper.LogImportRecordMapper;
import com.fly.caipiao.analysis.service.DataService;
import com.fly.caipiao.analysis.service.LogFileService;
import com.fly.caipiao.analysis.service.ReadDataService;
import com.fly.caipiao.analysis.thread.ReadDataThread;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("dataService")
public class DataServiceImpl implements DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);
    private final static Integer CORE_POOL_SIZE = 5;
    private final static Integer MAXIMUM_POOL_SIZE = 5;
    private final static Long KEEP_ALIVE_TIME = 0L;

    public final static ExecutorService EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue());

    @Autowired
    private ReadDataService readDataService;
    @Autowired
    private LogImportRecordMapper logImportRecordMapper;
    @Autowired
    private LogFileService logFileService;

    @Override
    @TimeConsuming("数据解析并保存")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void analysis(String name) {
        Boolean checkStatus = this.checkRepeat(name);
        if(!checkStatus){
            throw new AppException("\""+name+"\"重复导入");  // 重复导入处理
        }

        EXECUTOR.submit(new ReadDataThread(name,readDataService));
    }

    @Override
    @TimeConsuming("批量文件处解析并保存")
    public void analysis() {
        List<FileVO> files = logFileService.listDirFiles();
        for (FileVO fileVO : files){
            fileVO.setKey(MD5Encrypt.getEncrypt().encode(fileVO.getName()));
        }
        List<String> keys = logImportRecordMapper.listNameKeys(files);
        Map<String,String> map = new HashMap<>();
        for (String key : keys){
            map.put(key,key);
        }

        for (FileVO fileVO : files){
            if(!map.containsKey(fileVO.getKey())){  // 跳过有导入记录的文件

                EXECUTOR.submit(new ReadDataThread(fileVO.getName(),readDataService));

            }
        }
    }

    /**
     * 重复导入检查
     * @param name
     */
    private boolean checkRepeat(String name){
        String key = MD5Encrypt.getEncrypt().encode(name);
        int count = logImportRecordMapper.queryByNameKey(key);
        if(count > 0){
            return false;
        }
        return true;
    }


    private Long getLongTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }


}
