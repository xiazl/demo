package com.fly.caipiao.analysis.thread;

import com.fly.caipiao.analysis.entity.ResourcePlatformStatistics;
import com.fly.caipiao.analysis.framework.page.PageBean;
import com.fly.caipiao.analysis.framework.page.PageDataResult;
import com.fly.caipiao.analysis.service.MongoWriteService;
import com.fly.caipiao.analysis.service.PhoenixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author baidu
 * @date 2018/7/22 上午10:50
 * @description 资源-平台详情按天统计记录
 **/
public class ResourcePlatformThread implements Callable<Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResourcePlatformThread.class);
    private static final Integer SIZE = 5000;

    private Long time;
    private PhoenixService phoenixService;
    private MongoWriteService mongoWriteService;

    public ResourcePlatformThread(Long time, PhoenixService phoenixService, MongoWriteService mongoWriteService) {
        this.time = time;
        this.phoenixService = phoenixService;
        this.mongoWriteService = mongoWriteService;
    }

    @Override
    public Long call() {
        PageBean pageBean = new PageBean();
        pageBean.setiDisplayLength(SIZE);
        try {
            PageDataResult<ResourcePlatformStatistics> list = phoenixService.aggregationResourcePlatform(pageBean,time);
            mongoWriteService.insertBatchResourcePlatform(list.getAaData());

            int pages = (int) (list.getiTotalRecords()/SIZE + 1);
            if(pages > 1){
                for(int page = 2;page <= pages; page ++){
                    pageBean.setiDisplayStart((long) ((page -1)*SIZE));
                    list = phoenixService.aggregationResourcePlatform(pageBean,time);
                    mongoWriteService.insertBatchResourcePlatform(list.getAaData());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return time;
        }
        return null;
    }
}
