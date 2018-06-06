package com.south.worker.ui.login;

import android.content.Context;

/**
 * 描述   ：登录页面
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class ResetPasseordPresenter implements ResetPasswordContact.Presenter {

    Context mContext;
    ResetPasswordContact.View mView;

    public ResetPasseordPresenter(Context context, ResetPasswordContact.View view) {
        mContext = context;
        mView = view;
    }
}
