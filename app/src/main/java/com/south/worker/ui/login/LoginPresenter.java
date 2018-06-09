package com.south.worker.ui.login;

import android.content.Context;

import com.baselib.utils.LogUtils;
import com.baselib.utils.SharedPreferencesUtil;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.data.remote.UserRemoteDataSource;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 描述   ：登录页面
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class LoginPresenter implements LoginContact.Presenter {

    Context mContext;
    LoginContact.View mView;

    public LoginPresenter(Context context, LoginContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void login(final String userName, final String userPassword) {
        UserRepository.getInstance()
                .login(userName,userPassword)
                .subscribe(new LoadingSubscriber<UserLoginBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(UserLoginBean userLoginBean) {

                        if(userLoginBean.IsOk){
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_ID,userLoginBean.Id);
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_PART_ID,userLoginBean.BranchId);
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_NAME,userLoginBean.UserName);
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_MOBILE,userName);


                            if (mView.isRememberPassword()){
                                SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_PASSWORD,userPassword);
                                SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_REMEMBER_PASSWORD,true);
                            }


                            mView.goHome();
                        }else{
                            mView.showTipDialog(userLoginBean.Msg);
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });
    }
}
