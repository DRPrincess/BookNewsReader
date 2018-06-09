package com.south.worker.data.bean;

/**
 * 描述   ：读书心得
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class ReadThinkingBean {

    public int BookId;
    public String BookName;
    public int UserId;
    public String UserName;
    public String Content;

    public String title;
    public String content;

    public ReadThinkingBean(){

    }
    public ReadThinkingBean(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
