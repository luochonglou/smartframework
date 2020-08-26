package org.smartframework.common.utils.date;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author chonglou
 * @date 2019/3/415:25
 */
public abstract class LocalDateTimeUtil {

    /**
     * 将long类型的timestamp转为LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);

    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 格式化
     *
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String formatter(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(dateTime);
    }


    /**
     * 计算指定时间与当前时间毫秒差
     *
     * @param end
     * @return
     */
    public static long toMillis(LocalDateTime end) {
        Duration duration = Duration.between(LocalDateTime.now(), end);
        //相差毫秒数
        return duration.toMillis();
    }

    /**
     * 计算指定时间时间毫秒差
     *
     * @param end
     * @return
     */
    public static long toMillis(LocalDateTime begin, LocalDateTime end) {
        Duration duration = Duration.between(begin, end);
        //相差毫秒数
        return duration.toMillis();
    }

    /**
     * 获取几天后的开始时间
     *
     * @param time
     * @param days
     * @return
     */
    public static LocalDateTime getPlusDayStart(LocalDateTime time, long days) {
        LocalDate lo = time.toLocalDate().plusDays(days);
        return LocalDateTime.of(lo, LocalTime.MIN);
    }

    /**
     * 获取几天后的结束时间
     *
     * @param time
     * @param days
     * @return
     */
    public static LocalDateTime getPlusDayEnd(LocalDateTime time, long days) {
        return LocalDateTime.of(time.toLocalDate().plusDays(days), LocalTime.MAX);
    }

    /**
     * 获取一天的开始时间，2019,3,4 00:00
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间，2019,3,4 23:59:59.999999999
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    /**
     * 获取今天所剩时间毫秒数
     *
     * @return
     */
    public static long getToDayEndMilliSeconds() {
        return toMillis(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
    }

}
