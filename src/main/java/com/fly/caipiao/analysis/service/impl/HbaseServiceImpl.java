package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import com.fly.caipiao.analysis.entity.CDNLogEntity;
import com.fly.caipiao.analysis.entity.ErrorRecord;
import com.fly.caipiao.analysis.entity.HbaseEntity;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.mapper.ErrorRecordMapper;
import com.fly.caipiao.analysis.service.HbaseService;
import com.fly.caipiao.analysis.service.MongoWriteService;
import com.fly.caipiao.analysis.service.PhoenixService;
import com.fly.caipiao.analysis.thread.*;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author baidu
 * @date 2018/7/12 下午3:00
 * @description ${TODO}
 **/

@Service
public class HbaseServiceImpl implements HbaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseServiceImpl.class);
    private static final String TABLE_NAME = "cdn_logs";
    private static final String FAMILY_NAME = "USER";
    private static final Integer DEFAULT_STATUS = 0;
    private static final Random random = new Random();
    private final static Integer CORE_POOL_SIZE = 1;
    private final static Integer MAXIMUM_POOL_SIZE = 1;
    private final static Long KEEP_ALIVE_TIME = 300L;
    /**  防止连续导入时，数据计算先后顺序的问题，这里暂时限制为单线程 */
    private final static ExecutorService EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue());

//    private final static CompletionService<Long> COMPLETIONSERVICE = new ExecutorCompletionService<>(EXECUTOR);

    @Autowired
    private MongoWriteService mongoWriteService;
    @Autowired
    private PhoenixService phoenixService;
    @Autowired
    private Connection hbaseConnection;
    @Autowired
    private ErrorRecordMapper errorRecordMapper;

    @Override
    public PageDataResult<HbaseEntity> list(PageBean pageBean, String lastRowKey) {
        pageBean.setiDisplayLength(1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
//        lastRowKey = calendar.getTimeInMillis();
        Scan scan = new Scan();
        PageFilter pageFilter = new PageFilter(pageBean.getiDisplayLength());

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//        filterList.setReversed(true); // 倒着查
        filterList.addFilter(pageFilter);

//        Filter rowFilter2 = new RowFilter(CompareOperator.GREATER,
//                new BinaryComparator(Bytes.toBytes(lastRowKey)));
//        filterList.addFilter(rowFilter2);

        scan.setFilter(filterList);

//        Connection connection = HbaseConnection.getConnection();

        try {
            Table table = hbaseConnection.getTable(TableName.valueOf(TABLE_NAME));
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                System.out.println(result.getRow());
                for (Cell cell : result.rawCells()) {
                    System.out.println(new String(CellUtil.cloneValue(cell)));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @TimeConsuming("hbase插入")
    public void insertBatch(List<CDNLogEntity> list,List<String> ids,Long timeMillis) {
        // 创建表，初始化调用一次
//        this.createTable(TABLE_NAME);

//        Connection connection = HbaseConnection.getConnection();

        Table table;
        try {
            Map<String,String> map = this.queryTableByIds(ids);

            table = hbaseConnection.getTable(TableName.valueOf(TABLE_NAME));
            List<Put> lists = new ArrayList<>();

            for (CDNLogEntity entity : list) {
                // 去重
                if(map.containsKey(entity.getId())){
                    continue;
                }

                Put put = new Put(entity.getId().getBytes());
                /**  因为适用phoenix的缘故，字段名都用大写了，使用上比较方便一点  */
                put.addColumn(FAMILY_NAME.getBytes(), "IP".getBytes(), Bytes.toBytes(entity.getIp()));
                put.addColumn(FAMILY_NAME.getBytes(), "DATE_TIME".getBytes(), Bytes.toBytes(entity.getDateTime()));
                put.addColumn(FAMILY_NAME.getBytes(), "REFERER".getBytes(), this.getBytes(entity.getReferer()));
                put.addColumn(FAMILY_NAME.getBytes(), "TARGET_URL".getBytes(), Bytes.toBytes(entity.getTargetUrl()));
                put.addColumn(FAMILY_NAME.getBytes(), "CREATE_TIME".getBytes(), Bytes.toBytes(timeMillis));

                lists.add(put);
            }

            table.put(lists);
            table.close();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("数据插入hbase失败"+e.getMessage());
        }

    }

    /**
     * 通过Id查询
     * @param rowKeys
     * @return
     * @throws IOException
     */
    public Map queryTableByIds(List<String> rowKeys) throws IOException {
        List<Get> keys = new ArrayList();
        Table table = hbaseConnection.getTable( TableName.valueOf(TABLE_NAME));
        for (String key : rowKeys){
            Get get = new Get(Bytes.toBytes(key));
            keys.add(get);
        }

        Result[] results = table.get(keys);
        Map<String,String> map = new HashMap<>();
        for (Result result : results){
            if(result.getRow()!=null);
            {
                String row = Bytes.toString(result.getRow());
                map.put(row, row);
            }
        }
        return map;
    }


    @Override
    public void aggregationStatistics(Long time){
        new Thread(new AggregationThread(time)).start();
    }

    protected class AggregationThread implements Runnable{

        private Long time;

        public AggregationThread(Long time) {
            this.time = time;
        }

        @Override
        public void run() {
            List<Future<Long>> result = new ArrayList<>();

            result.add(EXECUTOR.submit(new PvThread(time, phoenixService, mongoWriteService)));
            result.add(EXECUTOR.submit(new UvThread(time, phoenixService, mongoWriteService)));
            result.add(EXECUTOR.submit(new PlatformThread(time, phoenixService, mongoWriteService)));
            result.add(EXECUTOR.submit(new ResourceThread(time, phoenixService, mongoWriteService)));
            result.add(EXECUTOR.submit(new ResourcePlatformThread(time, phoenixService, mongoWriteService)));

            try {
                for (int i = 4; i < 5; i++) {
                    Long returnValue = result.get(0).get();

                    switch (i) {
                        case 0:
                            if (returnValue != null) {
                                this.saveErrorRecord(returnValue, "PV");
                            }
                            break;
                        case 1:
                            if (returnValue != null) {
                                this.saveErrorRecord(returnValue, "UV");
                            }
                            break;
                        case 2:
                            if (returnValue != null) {
                                this.saveErrorRecord(returnValue, "platform");
                            }
                            break;
                        case 3:
                            if (returnValue != null) {
                                this.saveErrorRecord(returnValue, "resource");
                            }
                            break;
                        case 4:
                            if (returnValue != null) {
                                this.saveErrorRecord(returnValue, "resource_platform");
                            }
                            break;

                        default:
                    }
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            LOGGER.info("Aggregation Statistics Success!!!");
        }

        /**
         * 记录计算失败的记录，发现后手动重复处理
         * @param time
         * @param message
         */
        private void saveErrorRecord(Long time,String message){
            ErrorRecord record = new ErrorRecord();
            record.setCreateTime(new Date());
            record.setName(message);
            record.setStatus(DEFAULT_STATUS);
            record.setTime(time);

            errorRecordMapper.insert(record);
        }
    }


    private byte[] getBytes(String content){
        if(content == null){
            return null;
        }
        return Bytes.toBytes(content);
    }


}
