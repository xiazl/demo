package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import com.fly.caipiao.analysis.configuration.properties.PathProperties;
import com.fly.caipiao.analysis.entity.LogDownloadRecord;
import com.fly.caipiao.analysis.framework.excepiton.AppException;
import com.fly.caipiao.analysis.mapper.LogDownloadRecordMapper;
import com.fly.caipiao.analysis.service.DownloadService;
import com.fly.caipiao.analysis.web.controller.vo.CDNFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * @author baidu
 * @date 2018/8/1 下午4:14
 * @description ${TODO}
 **/

@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);

    private static final String SOURCE_PATH = "source_file";
    private static final String LOG_PATH = "log_file";
    private static final String HTTP_PREFIX = "http://";
    private final static String METHOD = "GET";
    private final static Integer DEFAULT_STATUS = 0;
    private final static Integer FAILED_STATUS = -1;

    @Autowired
    private PathProperties pathProperties;
    @Autowired
    private LogDownloadRecordMapper downloadRecordMapper;

    @Override
//    @Transactional(propagation = Propagation.REQUIRED,timeout = 1800,rollbackFor = Exception.class)
    public void downloadFile(CDNFileVO download) {
        int count = downloadRecordMapper.queryByNameKey(MD5Encrypt.getEncrypt().encode(download.getLogName()));
        if (count > 0){
            LOGGER.error("文件重复下载: "+download.getLogName());
            return;
        }
        String basePath = pathProperties.getPath();
        String logPath = download.getLogPath();
        String fileName = download.getLogName();
        Integer fileSize = download.getLogSize();

        this.downFileToServer(basePath,logPath,fileSize);

        saveDownloadRecord(fileName,fileSize,logPath,DEFAULT_STATUS);

        unGzipFile(basePath,fileName);
    }


    /**
     * @从制定URL下载文件并保存到指定目录
     * @param basePath 文件将要保存的目录
     * @param fileSize 文件大小
     * @param logPath 请求的路径
     * @return
     */
    public void downFileToServer(String basePath,String logPath,Integer fileSize){
        String sourceFilePath = basePath+File.separator+SOURCE_PATH+File.separator;

        String fileName = logPath.substring(logPath.lastIndexOf("/")+1,logPath.indexOf("?"));

        File file=new File(basePath);

        if (!file.exists())
        {
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            URL httpUrl=new URL(HTTP_PREFIX+logPath);
            conn=(HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod(METHOD);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            conn.connect();
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);

            fileOut = new FileOutputStream(sourceFilePath+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            saveDownloadRecord(fileName,fileSize,logPath,FAILED_STATUS);
            throw new AppException("下载失败");
        }
    }


    /**
     * 记录下载记录
     * @param fileName
     */
    private void saveDownloadRecord(String fileName, Integer fileSize,String logPath,Integer status){
        String key = MD5Encrypt.getEncrypt().encode(fileName);
        int count = downloadRecordMapper.queryByNameKey(key);
        if(count > 0){
            downloadRecordMapper.queryByNameKey(key);
            return;
        }

        LogDownloadRecord record = new LogDownloadRecord();
        record.setName(fileName);
        record.setKey(MD5Encrypt.getEncrypt().encode(fileName));
        record.setSize(fileSize);
        record.setLogPath(logPath);
        record.setCreateTime(new Date());
        record.setStatusFlag(status);
        downloadRecordMapper.insert(record);
    }


    /**
     * 解压gzip文件到指定目录
     * @param basePath 路径
     * @param fileName 文件名
     */
    public void unGzipFile(String basePath,String fileName) {
        String sourceFilePath = basePath+File.separator+SOURCE_PATH+File.separator+fileName;
        String targetPath = basePath+File.separator+LOG_PATH+File.separator+fileName.substring(0,
                fileName.lastIndexOf("."));

        try {
            //建立gzip压缩文件输入流
            FileInputStream fileInput = new FileInputStream(sourceFilePath);
            //建立gzip解压工作流
            GZIPInputStream gzInput = new GZIPInputStream(fileInput);
            //建立解压文件输出流
            FileOutputStream fileOut = new FileOutputStream(targetPath);

            int num;
            byte[] buf=new byte[1024];

            while ((num = gzInput.read(buf,0,buf.length)) != -1)
            {
                fileOut.write(buf,0,num);
            }

            gzInput.close();
            fileOut.close();
            fileInput.close();
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw new AppException("解压文件失败");
        }
    }

}
