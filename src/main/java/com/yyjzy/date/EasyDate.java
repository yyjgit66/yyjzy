package com.yyjzy.date;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 生成容易上手的日期操作
 * @author yyj
 *
 */
public class EasyDate {

   static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 字符串转日期
     * @param date
     * @return
     */
    public static LocalDate stringToDate(String date){
        if(null==date || date.length()==0) return null;
        LocalDate parse = LocalDate.parse(date, dateFormatter);
        return parse;
    }

    /**
     * 字符串转时间
     * @param date
     * @return
     */
    public static LocalDate stringToDateTime(String date){
        if(null==date || date.length()==0) return null;
        LocalDate parse = LocalDate.parse(date, dateTimeFormatter);
        return parse;
    }


    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(LocalDate date){
        if(null==date)return null;
        String format = dateFormatter.format(date);
        return format;

    }


    /**
     * 时间转字符串
     * @param date
     * @return
     */
    public static String dateTimeToString(LocalDate date){
        if(null==date)return null;
        String format = dateTimeFormatter.format(date);
        return format;

    }

}
