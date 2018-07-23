package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.entity.PvStatistics;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.service.MongoWriteService;
import com.fly.caipiao.analysis.service.PhoenixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author baidu
 * @date 2018/7/20 上午10:39
 * @description ${TODO}
 **/
public class PvThread implements Callable<Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(PvThread.class);
    private static final Integer SIZE = 50000;


    private Long time;
    private PhoenixService phoenixService;
    private MongoWriteService mongoWriteService;

    public PvThread(Long time, PhoenixService phoenixService,MongoWriteService mongoWriteService) {
        this.time = time;
        this.phoenixService = phoenixService;
        this.mongoWriteService = mongoWriteService;
    }

    @Override
    public Long call() {
        PageBean pageBean = new PageBean();
        pageBean.setiDisplayLength(SIZE);
        try {
            PageDataResult<PvStatistics> list = phoenixService.aggregationPv(pageBean,time);
            mongoWriteService.insertBatchPv(list.getData());

            int pages = (int) (list.getiTotalRecords()/SIZE + 1);
            if(pages > 1){
                for(int page = 2;page <= pages; page ++){
                    pageBean.setiDisplayStart((long) ((page -1)*SIZE));
                    list = phoenixService.aggregationPv(pageBean,time);
                    mongoWriteService.insertBatchPv(list.getData());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return time;
        }
        return null;
    }
}
