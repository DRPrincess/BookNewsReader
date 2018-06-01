package com.south.worker.ui.login;

import android.content.Context;

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
}
