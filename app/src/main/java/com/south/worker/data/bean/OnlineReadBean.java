package com.south.worker.data.bean;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadBean {

    public String type;
    public OnlineBookBean mOnlineBookBean;
    public  MyBookBean mMyBookBean;
    public  ReadThinkingBean mReadThinkingBean;

    public OnlineReadBean(String type, OnlineBookBean onlineBookBean) {
        this.type = type;
        mOnlineBookBean = onlineBookBean;
    }

    public OnlineReadBean(String type, MyBookBean myBookBean) {
        this.type = type;
        mMyBookBean = myBookBean;
    }

    public OnlineReadBean(String type,ReadThinkingBean readThinkingBean) {
        this.type = type;
        mReadThinkingBean = readThinkingBean;
    }
}
