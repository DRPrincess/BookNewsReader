package com.baselib.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述   ：数学运算加密工具类
 * <p>
 * 作者   ：Created by DR on 2018/6/6.
 */

public class MathUtils {


    /**
     * 给字符串 MD5 加密
     * @param str
     * @return
     */
    public static String md5(String str){

        if(TextUtils.isEmpty(str)){
            return str;
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] datas = str.getBytes("UTF-8");

            md5.update(datas);

            return new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return  str;
    }
}
