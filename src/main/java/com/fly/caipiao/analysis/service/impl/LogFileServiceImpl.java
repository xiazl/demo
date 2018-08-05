package com.fly.caipiao.analysis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fly.caipiao.analysis.common.utils.HttpClientUtil;
import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import com.fly.caipiao.analysis.common.utils.SignatureUtils;
import com.fly.caipiao.analysis.configuration.properties.PathProperties;
import com.fly.caipiao.analysis.entity.LogDownloadRecord;
import com.fly.caipiao.analysis.entity.LogImportRecord;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.framework.page.*;
import com.fly.caipiao.analysis.mapper.LogDownloadRecordMapper;
import com.fly.caipiao.analysis.mapper.LogImportRecordMapper;
import com.fly.caipiao.analysis.service.DownloadService;
import com.fly.caipiao.analysis.service.LogFileService;
import com.fly.caipiao.analysis.thread.DownloadThread;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
import com.fly.caipiao.analysis.web.controller.vo.FileVO;
import com.fly.caipiao.analysis.web.controller.vo.LogDownloadVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description ${TODO}
 **/

@Service("logFileService")
public class LogFileServiceImpl implements LogFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFileServiceImpl.class);
    private static final String SOURCE_PATH = "source_file";
    private static final String LOG_PATH = "log_file";
    private final static Integer STATUS_FLAG = 1;
    private final static Integer PAGE_SIZE = 100;
    private final static Integer CORE_POOL_SIZE = 2;
    private final static Integer MAXIMUM_POOL_SIZE = 2;
    private final static Long KEEP_ALIVE_TIME = 0L;

    public final static ExecutorService EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue());

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private LogImportRecordMapper logImportRecordMapper;
    @Autowired
    private PathProperties pathProperties;
    @Autowired
    private LogDownloadRecordMapper downloadRecordMapper;
    @Autowired
    private LogImportRecordMapper recordMapper;


    @Override
    public PageDataResult<LogImportRecord> list(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List list = logImportRecordMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public PageDataResult<CDNFileVO> listOnline(PageBean pageBean, CDNDownloadConditionVO conditionVO) {

        String url = this.getCDNQueryUrl(pageBean,conditionVO);

        String result = HttpClientUtil.httpGet(url);

        try {
            LogDownloadVO downloadVO = JSON.parseObject(result, new TypeReference<LogDownloadVO>() {});

            if(downloadVO.getDomainLogModel() == null){
                return PageHelp.getDataResult(new ArrayList<>(),0L);
            }
            List<CDNFileVO> domainLogDetail = downloadVO.getDomainLogModel().getDomainLogDetails()
                    .getDomainLogDetail();

            for(CDNFileVO download : domainLogDetail){
                download.setKey(MD5Encrypt.getEncrypt().encode(download.getLogName()));
            }
            if(domainLogDetail.size() > 0) {
                List<String> list = downloadRecordMapper.queryByKeys(domainLogDetail);
                Map<String,String> map = new HashMap<>();
                for(String key : list){
                    map.put(key,key);
                }

                for(CDNFileVO download : domainLogDetail){
                    if(map.containsKey(download.getKey())){
                        download.setStatus(STATUS_FLAG);
                    }
                }

            }

            return PageHelp.getDataResult(domainLogDetail,Long.valueOf(downloadVO.getTotalCount()));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            throw new AppException("CDN日志查询返回结果解析异常");
        }

    }

    @Override
    public List<FileVO> listDirFiles() {
        // 获取制定目录下的日志文件名
        List<FileVO> list = getDirFiles();
        if (list.size() == 0){
            return new ArrayList<>();
        }
        Map<String,String> map = productKeys(list);

        for (FileVO fileVO : list){
            if (map.containsKey(fileVO.getKey())){
                fileVO.setStatus(STATUS_FLAG);
            }
        }

        return list;
    }

    @Override
    public void downloadFileBatch(List<CDNFileVO> list) {
        for (CDNFileVO fileVO : list){
            EXECUTOR.submit(new DownloadThread(fileVO,downloadService));
        }
    }

    @Override
    public void downloadFileAll(CDNDownloadConditionVO conditionVO) {
        PageBean pageBean = new PageBean();
        pageBean.setiDisplayLength(PAGE_SIZE);

        PageDataResult<CDNFileVO> result = this.listOnline(pageBean,conditionVO);
        List<CDNFileVO> data = result.getAaData();
        if (result.getiTotalRecords() > 0){
            this.downloadFileBatch(removeRepeat(data));
        }

        int pages = (int) (result.getiTotalRecords()/PAGE_SIZE + 1);
        if(pages > 1){
            for(int page = 2;page <= pages; page ++){
                pageBean.setiDisplayStart((long) ((page -1)*PAGE_SIZE));

                result = this.listOnline(pageBean,conditionVO);

                data = result.getAaData();
                if (result.getiTotalRecords() > 0){
                    this.downloadFileBatch(removeRepeat(data));
                }
            }
        }

    }

    @Override
    public PageDataResult<LogDownloadRecord> downRecord(PageBean pageBean, ConditionVO conditionVO) {
        PageHelper.startPage(pageBean.getCurrent(),pageBean.getiDisplayLength());
        List<LogDownloadRecord> list = downloadRecordMapper.list(conditionVO);
        return PageHelp.getDataResult(list);
    }

    @Override
    public void reDownload() {
        ConditionVO conditionVO = new ConditionVO();
        conditionVO.setKeyword("-1");
        List<LogDownloadRecord> list = downloadRecordMapper.list(conditionVO);
        List<CDNFileVO> downloadFile = new ArrayList<>();
        for (LogDownloadRecord record : list){
            CDNFileVO file = new CDNFileVO();
            file.setLogName(record.getName());
            file.setLogPath(record.getLogPath());
            file.setLogSize(record.getSize());
            downloadFile.add(file);
        }

        if (downloadFile.size() > 0){
            downloadFileBatch(downloadFile);
        } else {
            throw new AppException("没有下载失败记录");
        }
    }

    @Override
    public void clearFiles() {
        String basePath = pathProperties.getPath() + File.separator + SOURCE_PATH;
        // 删除源文件
        this.deleteSourceFiles(basePath);
        // 删除日志文件
        List<FileVO> list = getDirFiles();
        if (list.size() == 0){
            return;
        }
        Map<String,String> map = productKeys(list);

        for (FileVO fileVO : list){
            if (map.containsKey(fileVO.getKey())){
                this.deleteFile(fileVO.getName());
            }
        }

    }

    /**
     * 去掉已下载的数据
     * @param data
     * @return
     */
    private List<CDNFileVO> removeRepeat(List<CDNFileVO> data){
        Iterator<CDNFileVO> iterator = data.iterator();
        while (iterator.hasNext()){
            CDNFileVO fileVO = iterator.next();
            if (fileVO.getStatus() != 0){
                iterator.remove();
            }
        }

        return data;
    }

    /**
     * 生成key值
     * @param list
     */
    private Map<String,String> productKeys(List<FileVO> list){

        for (FileVO fileVO : list){
            fileVO.setKey(MD5Encrypt.getEncrypt().encode(fileVO.getName()));
        }
        List<String> keys = logImportRecordMapper.listNameKeys(list);
        Map<String,String> map = new HashMap<>();
        for (String key : keys){
            map.put(key,key);
        }
        return map;
    }

    /**
     * 获取日志目录文件
     */
    private List<FileVO> getDirFiles(){
        String basePath = pathProperties.getPath()+File.separator+LOG_PATH;
        List<FileVO> list = new ArrayList<>();
        this.getFiles(list,basePath);

        return list;
    }


    /**
     * 递归读取日志
     * @param list
     * @param path
     */
    private void getFiles(List<FileVO> list,String path){
        File dir = new File(path);
        String[] files = dir.list();
        String dirPath;
        for(String name : files){
            dirPath = path+File.separator + name;
            File file = new File(dirPath);
            if (file.isDirectory()){
                getFiles(list,dirPath);
            } else {
                FileVO fileVO = new FileVO(dirPath,file.length());
                list.add(fileVO);
            }
        }
    }

    /**
     * 单个文件删除
     * @param filePath 文件路径
     */
    private void deleteFile(String filePath){
        File file = new File(filePath);
        if (file.isFile()){
            file.delete();
        }
    }


    /**
     * 递归源删除文件
     * @param path
     */
    private void deleteSourceFiles(String path){
        File dir = new File(path);
        String[] files = dir.list();
        String dirPath;
        for(String name : files){
            dirPath = path+File.separator + name;
            File file = new File(dirPath);
            if (file.isDirectory()){
                deleteSourceFiles(dirPath);
            } else {
                file.delete();
            }
        }
    }

    /**
     * CDN日志查询URL拼接
     * @param pageBean 分页参数
     * @param conditionVO 条件
     * @return URL
     */
    private String getCDNQueryUrl(PageBean pageBean,CDNDownloadConditionVO conditionVO){
        Map<String,String> param = new HashMap<>();
        param.put("DomainName", conditionVO.getDomain());
        param.put("PageNumber",String.valueOf(pageBean.getCurrent()));
		param.put("PageSize",String.valueOf(pageBean.getiDisplayLength()));

		if(conditionVO.getStartTime()!=null) {
            param.put("StartTime", SignatureUtils.getCurrentUtcTime(conditionVO.getStartTime()));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-1);
            param.put("StartTime",SignatureUtils.getCurrentUtcTime(calendar.getTime()));  // 最近一个月
        }
        if (conditionVO.getEndTime()!=null) {
            param.put("EndTime", SignatureUtils.getCurrentUtcTime(conditionVO.getEndTime()));
        } else {
            param.put("EndTime",SignatureUtils.getCurrentUtcTime(new Date()));  // 最近一个月
        }

        String url = SignatureUtils.getUrl(param);

        return url;
    }

}
