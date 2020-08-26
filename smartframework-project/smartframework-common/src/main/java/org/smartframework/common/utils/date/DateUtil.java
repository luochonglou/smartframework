package org.smartframework.common.utils.date;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author chonglou
 * @date 2019/5/3016:32
 */
public class DateUtil {
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     */
    public static final String DATE_DFTT = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DATE_SHORT = "yyyy-MM-dd";
    /**
     * 日期格式：yyyy-MM
     */
    public static final String MONTH_SHORT = "yyyy-MM";
    /**
     * 日期格式 yyyyMMdd
     */
    public static final String DATE_NORMAL = "yyyyMMdd";

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getNowString() {
        return dateFormat(new Date());
    }

    /**
     * 从时间格式化字符串
     * 默认格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        return dateFormat(date, DATE_LONG);
    }

    /**
     * 从时间格式化字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (StringUtils.isEmpty(format)) {
            format = DATE_LONG;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 从字符串格式化时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date dateFromString(String dateStr, String format) {
        if (StringUtils.isNotEmpty(dateStr) && StringUtils.isNotEmpty(format)) {
            DateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 增加时间数
     *
     * @param date
     * @param field
     * @param interval
     * @return
     */
    public static Date addOnField(Date date, int field, int interval) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(field, interval);
        return ca.getTime();
    }

    /**
     * 获取某个块
     *
     * @param date
     * @param field
     * @return
     */
    public static int getFieldOfDate(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }


    /**
     * 去除时分秒
     *
     * @param date
     * @return
     */
    public static Date getCleanDate(Date date) {
        date = date == null ? new Date() : date;
        String cleanDateStr = DateUtil.dateFormat(date, DateUtil.DATE_SHORT);
        return DateUtil.dateFromString(cleanDateStr, DateUtil.DATE_SHORT);
    }
}
