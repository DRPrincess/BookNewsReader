package com.south.worker.data.remote;

import com.south.worker.data.UserDataSource;

/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class UserRemoteDataSource implements UserDataSource {

    private  static  UserRemoteDataSource INSTANCE;
    public static UserRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }
}
