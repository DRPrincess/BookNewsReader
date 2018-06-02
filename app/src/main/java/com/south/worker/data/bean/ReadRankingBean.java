package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class ReadRankingBean {

    public  String order;
    public String userName;
    public String userAvater;
    public String readTime;


    public ReadRankingBean(String order, String userName, String userAvater, String readTime) {
        this.order = order;
        this.userName = userName;
        this.userAvater = userAvater;
        this.readTime = readTime;
    }
}
