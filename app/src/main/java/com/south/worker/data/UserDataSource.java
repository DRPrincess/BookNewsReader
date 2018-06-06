package com.south.worker.data;

import com.south.worker.data.bean.UserLoginBean;

import io.reactivex.Observable;


/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public interface UserDataSource  {

    Observable<UserLoginBean> login(String userName,String password);
}
