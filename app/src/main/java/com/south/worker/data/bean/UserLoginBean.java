package com.south.worker.data.bean;

/**
 * 描述   ：用户登录
 * <p>
 * 作者   ：Created by DR on 2018/6/5.
 */

public class UserLoginBean {


    public String UserName;
    public boolean IsOk;
    public String Msg;
    public int Id;
    public int BranchId;


    @Override
    public String toString() {
        return "UserLoginBean{" +
                "IsOk=" + IsOk +
                ", Msg='" + Msg + '\'' +
                ", Id=" + Id +
                ", BranchId=" + BranchId +
                '}';
    }
}
