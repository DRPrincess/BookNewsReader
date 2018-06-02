package com.baselib.utils;

import android.util.Log;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class LogUtils {

    private static final String TAG = "DRPrincess";

    private static boolean logSwitch = true;

    public static void v(String msg) {
        if (logSwitch)
            android.util.Log.v(TAG, msg);
    }


    public static void v(String msg, Throwable t) {
        if (logSwitch)
            android.util.Log.v(TAG, msg, t);
    }

    public static void d(String log){
        if(logSwitch){
            Log.d(TAG, log);
        }
    }

    public static void i(String msg) {
        if (logSwitch)
            android.util.Log.i(TAG, msg);
    }

    public static void i(String msg, Throwable t) {
        if (logSwitch)
            android.util.Log.i(TAG, msg, t);
    }

    public static void w(String msg) {
        if (logSwitch)
            android.util.Log.w(TAG, msg);
    }

    public static void w(String msg, Throwable t) {
        if (logSwitch)
            android.util.Log.w(TAG, msg, t);
    }

    public static void e(String msg) {
        if (logSwitch)
            android.util.Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable t) {
        if (logSwitch)
            android.util.Log.e(TAG, msg, t);
    }

    public static void e(Throwable t) {
        if (logSwitch)
            android.util.Log.e(TAG, android.util.Log.getStackTraceString(t));
    }
}
