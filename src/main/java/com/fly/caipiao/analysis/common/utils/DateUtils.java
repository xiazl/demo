package com.fly.caipiao.analysis.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author baidu
 * @date 2018/6/28 下午3:53
 * @description 时间处理
 **/
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public enum Formatter{
        YYYY_MM_DD("yyyy-MM-dd"),
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss");
        private String value;

        Formatter(String value) {
            this.value = value;
        }
    }

    /**
     * 获取 时间区域里的 yyyy-mm-dd 包括 开始时间和结束时间
     *
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return
     */
    public static List<String> getListDay(Date startTime,
                                            Date endTime) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);
            endCalendar.setTime(endTime);
            SimpleDateFormat df = new SimpleDateFormat(Formatter.YYYY_MM_DD.value);
            List<String> list = new ArrayList<String>();
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
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * 获取时间区间内 的所有月份 yyyy-mm
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getListMonth(Date startDate, Date endDate) {
        try {
            Map<String, List<String>> xlist = new HashMap<String, List<String>>();
            List<String> list = new ArrayList<String>();
            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

            @SuppressWarnings("deprecation")
            int months = (endDate.getYear() - startDate.getYear()) * 12
                    + (endDate.getMonth() - startDate.getMonth());
            for (int i = 0; i <= months; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, i);
                list.add(monthFormat.format(calendar.getTime()));
            }
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }
}
