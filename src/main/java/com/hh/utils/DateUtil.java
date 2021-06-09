package com.hh.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
    /**
     * 格式化毫秒数 时间
     * @param time
     * @param fmt ,fmt为空的时候，默认是yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(Long time, String fmt) {
     return    formatTime(getTime(time), fmt);
    }

    /**
     * 根据毫秒数 获取当前时间
     * @param time
     * @return
     */
    public static Timestamp getTime(Long time) {
        return new Timestamp(time);
    }
    /**
     * 格式化 时间
     * @param time
     * @param fmt ,fmt为空的时候，默认是yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(Timestamp time, String fmt) {
        if (time!=null) {
            if (fmt == null) fmt = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
            return myFormat.format(time);
        } else {
            return "";
        }
    }
    /*获取两个时间相差的分钟*/
    public static Long getMin(Long stime,Long etime) {
        long between = stime - etime;
//        long day = between / (24 * 60 * 60 * 1000);
//        long hour = (between / (60 * 60 * 1000) - day * 24) ;
//        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60) ;
//        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60) ;
        long min = between/(60*1000);
        return min;
    }




}
