package com.baselib.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/9.
 */

public class TimeUtils {

     /**
      * "SysCreateTime": "\/Date(1528476080379)\/"
      * 将时间转换为 "yyyy-MM-dd" 时间
      * @param time
      * @return
      */
     public static String transformDateToJavaTime(String time){

          if(TextUtils.isEmpty(time)){
               return null;
          }
          time=time.replace("/Date(","").replace(")/","");
          Date date = new Date(Long.parseLong(time));
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          return format.format(date);

     }

     /**
      * "SysCreateTime": "\/Date(1528476080379)\/"
      * 将时间转换为 "yyyy-MM-dd" 时间
      * @param dateString
      * @return
      */
     public static Date transformJavaTimeToDate(String dateString){

          String rex = "/Date[(](.*?)[)]/";
          Pattern pattern = Pattern.compile(rex);
          Matcher m = pattern.matcher(dateString);
          if(m.find()){
               String str = m.group(1);
               String time = str.substring(0,str.length()-5);
               //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               try {
                    return new Date(Long.parseLong(time));
               } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
               }
          }else{
               System.out.println("没有匹配到");

          }
          return null;
     }


     /**
      * 获取当前全部时间
      *
      * @return 当前时间
      */
     public static String getCurrentDate() {
          return getCurrentDate("yyyy-MM-dd HH:mm:ss");
     }

     /**
      * 获取当前全部时间
      *
      * @return 当前时间
      */
     public static String getCurrentDate(String pattern) {
          SimpleDateFormat formatter = new SimpleDateFormat(pattern);
          Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
          String time = formatter.format(curDate);
          return time;
     }
     /**
      * 获取两个两个日期之间的分钟差
      * @param oldDataStr
      * @param dataStr
      * @return
      */
     public static long getMinuteBetweenTwoDate(String oldDataStr,String dataStr){

          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date now = null;
          try {
               now = df.parse(dataStr);
               Date date=df.parse(oldDataStr);
               long l=now.getTime()-date.getTime();
               long day=l/(24*60*60*1000);
               long hour=(l/(60*60*1000)-day*24);
               long min=((l/(60*1000))-day*24*60-hour*60);
               long s=(l/1000-day*24*60*60-hour*60*60-min*60);
               return min;
          } catch (ParseException e) {
               e.printStackTrace();
          }

          return 0;

     }




     /**
      * 将某个格式的时间字符串转换为另一个格式的时间字符串
      * @param dateString
      * @param oldPattern
      * @param newPattern
      * @return
      */
     public static String formatTimeStr(String dateString,String oldPattern,String newPattern)  {

          if(TextUtils.isEmpty(dateString)){
               return null;
          }

          if(TextUtils.isEmpty(oldPattern)){
               return null;
          }

          if(TextUtils.isEmpty(newPattern)){
               return null;
          }

          SimpleDateFormat sdf = new SimpleDateFormat(oldPattern);
          try {
               Date date = sdf.parse(dateString);
               SimpleDateFormat newSdf = new SimpleDateFormat(newPattern);
               return  newSdf.format(date);

          } catch (ParseException e) {
               e.printStackTrace();

          }

          return null;

     }


}
