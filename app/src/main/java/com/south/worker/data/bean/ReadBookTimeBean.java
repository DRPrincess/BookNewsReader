package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/12.
 */

public class ReadBookTimeBean {

    public int UserId;
    public int BranchId;
    public int BookId;
    public long LengthofReadingTime;
    public String Time;
    public int  Type; //1 是收藏读书 0 是大众图书
    public int  Num = 1;

}
