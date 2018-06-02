package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class PartActivityBean {
    public String title;
    public String content;
    public int image;
    public String url;

    public PartActivityBean(String title, String content, int image, String url) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.url = url;
    }
}
