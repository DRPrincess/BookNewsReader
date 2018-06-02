package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyBookBean {


    public int image;
    public String number;
    public String readTime;
    public String url;

    public MyBookBean(int image, String number, String readTime, String url) {
        this.image = image;
        this.number = number;
        this.readTime = readTime;
        this.url = url;
    }
}
