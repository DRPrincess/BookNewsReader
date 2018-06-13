package com.south.worker.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class ReadRankingBean implements Serializable{

    public  String order;
    @SerializedName("RealName")
    public String userName;
    public String userAvater;
    @SerializedName("Lengthof")
    public String readTime;
    public  int UserId;
    public  String BranchId;
    public  String BranchName;
    public  int Num;


    public ReadRankingBean() {
    }

    public ReadRankingBean(String order, String userName, String userAvater, String readTime) {
        this.order = order;
        this.userName = userName;
        this.userAvater = userAvater;
        this.readTime = readTime;
    }
}
