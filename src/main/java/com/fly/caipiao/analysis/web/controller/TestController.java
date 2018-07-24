package com.fly.caipiao.analysis.web.controller;

import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.framework.response.ResponseData;
import com.fly.caipiao.analysis.framework.response.Result;
import com.fly.caipiao.analysis.service.HbaseService;
import com.fly.caipiao.analysis.service.SparkService;
import com.fly.caipiao.analysis.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author baidu
 * @date 2018/6/19 下午3:09
 * @description ${TODO}
 **/

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final Integer LENGTH = 10000000;
    private static final Integer SIZE = 50000;
    private static String[] dateStringArray;
    private static final Random random = new Random();

    @Autowired
    private TestService testService;
    @Autowired
    private SparkService sparkService;
    @Autowired
    private HbaseService hbaseService;

    static {
        List<String> list = new ArrayList<String>();
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar.setTime(new Date());
            startCalendar.add(Calendar.DAY_OF_MONTH,-300);
            endCalendar.setTime(new Date());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            endCalendar.add(Calendar.DAY_OF_MONTH, 1);
            while (true) {
                if (startCalendar.getTimeInMillis() < endCalendar
                        .getTimeInMillis()) {
                    list.add(df.format(startCalendar.getTime()));
                } else {
                    break;
                }
                startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        dateStringArray = list.toArray(new String[]{});
    }

    @RequestMapping("/index")
    public String index() {
        return "user";
    }

    /**
     * 文件导入记录
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Result find(){
        List<CDNLogEntity> list =  testService.find();
        return ResponseData.success(list);
    }

    /**
     * hbase数据计算失败时，补救
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping("/aggregation")
    public Result aggregation(Long time){
        hbaseService.aggregationStatistics(time);
        return ResponseData.success();
    }

    /**
     * 清除mongo数据，测试用的
     * @return
     */
    @ResponseBody
    @RequestMapping("/clear")
    public Result clear(){
        testService.clear();
        return ResponseData.success();
    }


    @ResponseBody
    @RequestMapping("/insert")
    @TimeConsuming("插入测试")
    public Result insert(Integer length){
        if(length==null){
            length = LENGTH;
        }
        List<CDNLogEntity> list = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        // 记录当前时间戳
        Long timeMillis = new Date().getTime();

        for(int i = 1;i <= length;i++) {
            if(i % SIZE == 0) {
                long startTime = System.currentTimeMillis();
                testService.insertBatch(list,ids,timeMillis);
                long endTime = System.currentTimeMillis();
                System.out.println(SIZE+"条耗时："+String.valueOf(endTime - startTime));

                list = new ArrayList<>();
                ids = new ArrayList<>();
            }
            CDNLogEntity entity = new CDNLogEntity();
            entity.setTargetUrl(getHttp());
            entity.setReferer(getHttp());
            entity.setIp(getIp());
            entity.setDateTime(getDateString());
            entity.setId();

            ids.add(entity.getId());
            list.add(entity);
        }

        testService.insertBatch(list,ids,timeMillis);

        return ResponseData.success();
    }

    /**
     * spark写入测试
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Result save(){
        sparkService.load();
        return ResponseData.success();
    }

    private String getDateString(){
        int size = dateStringArray.length;
        int index = new Random().nextInt(size);
        return dateStringArray[index];
    }

    /**
     * 产品IP地址（测试用的）
     * @return
     */
    private String getIp(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<4;i++){
            stringBuilder.append(random.nextInt(255));
            stringBuilder.append(".");
        }
        int length = stringBuilder.length()-1;
        return stringBuilder.toString().substring(0,length);
    }

    /**
     * 产品模拟网址数据（测试用的）
     * @return
     */
    private String getHttp(){
//        String[] content = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
//                "p","q","r","s","t","u","v","w","x","y","z"};
//        StringBuilder sb = new StringBuilder();
//        int m = new Random().nextInt(26);
//        Random random = new Random();
//
//        for(int i = 0;i < m ;i ++){
//            sb.append(content[random.nextInt(26)]);
//        }
//        return "http://"+sb.toString()+"/com";

        String content = "abcdefghijklmnopqrstuvwxyz";
        int length = 4 + new Random().nextInt(6);
        int m = new Random().nextInt(26-length);
        int n = m + length;

        return "http://"+content.substring(m,n)+".com";
    }


}
