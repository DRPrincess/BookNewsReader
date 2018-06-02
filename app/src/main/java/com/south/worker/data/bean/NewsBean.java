package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class NewsBean {

    public String title;
    public int image;
    public String url;

    public NewsBean(String title, int image, String url) {
        this.title = title;
        this.image = image;
        this.url = url;
    }
}
