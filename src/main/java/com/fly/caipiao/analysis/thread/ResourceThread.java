package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.entity.ResourceStatistics;
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
 * @description 推广资源
 **/
public class ResourceThread implements Callable<Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResourceThread.class);
    private static final Integer SIZE = 5000;


    private Long time;
    private PhoenixService phoenixService;
    private MongoWriteService mongoWriteService;

    public ResourceThread(Long time, PhoenixService phoenixService, MongoWriteService mongoWriteService) {
        this.time = time;
        this.phoenixService = phoenixService;
        this.mongoWriteService = mongoWriteService;
    }

    @Override
    public Long call() {
        PageBean pageBean = new PageBean();
        pageBean.setiDisplayLength(SIZE);
        try {
            PageDataResult<ResourceStatistics> list = phoenixService.aggregationResource(pageBean,time);
            mongoWriteService.insertBatchResource(list.getAaData());

            int pages = (int) (list.getiTotalRecords()/SIZE + 1);
            if(pages > 1){
                for(int page = 2;page <= pages; page ++){
                    pageBean.setiDisplayStart((long) ((page -1)*SIZE));
                    list = phoenixService.aggregationResource(pageBean,time);
                    mongoWriteService.insertBatchResource(list.getAaData());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return time;
        }
        return null;
    }
}
