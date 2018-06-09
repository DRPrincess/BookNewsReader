package com.south.worker.data;

import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.NetHelper;
import com.south.worker.data.network.RequestException;
import com.south.worker.data.remote.UserRemoteDataSource;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public Observable<UserInfoBean> getUserInfo(int userId) {
        return NetHelper.createService(UserRemoteDataSource.class)
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> editUserInfo(UserInfoBean bean) {
        return NetHelper.createService(UserRemoteDataSource.class)
                .editUserInfo(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> changePassword(final int userId, String password, final String newPassword) {
        return NetHelper.createService(UserRemoteDataSource.class)
                .checkPassword(userId,password)
                .concatMap(new Function<RespondBean, ObservableSource<? extends RespondBean>>() {
                    @Override
                    public ObservableSource<? extends RespondBean> apply(RespondBean respondBean) throws Exception {

                        if(respondBean.IsOk){
                            return NetHelper.createService(UserRemoteDataSource.class).changePassword(userId,newPassword);

                        }
                        return Observable.error(new RequestException(respondBean.Msg));

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
