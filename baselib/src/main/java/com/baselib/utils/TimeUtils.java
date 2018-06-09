package com.baselib.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
      * @param time
      * @return
      */
     public static String transformJavaTimeToDate(String time){

          if(TextUtils.isEmpty(time)){
               return null;
          }
          time=time.replace("/Date(","").replace(")/","");
          Date date = new Date(Long.parseLong(time));
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          return format.format(date);

     }

}
