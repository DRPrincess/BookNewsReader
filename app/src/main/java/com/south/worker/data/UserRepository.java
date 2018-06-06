package com.south.worker.data;

import android.support.annotation.NonNull;

import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.NetHelper;
import com.south.worker.data.remote.UserRemoteDataSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DuanRui on 2018/5/31.
 */

public class UserRepository  implements UserDataSource{

    private static UserRepository instance;

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<UserLoginBean> login(String userName, String password) {
      return NetHelper.createService(UserRemoteDataSource.class)
                .login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
