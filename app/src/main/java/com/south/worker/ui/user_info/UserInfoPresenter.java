package com.south.worker.ui.user_info;

import android.content.Context;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class UserInfoPresenter implements UserInfoContact.Presenter {

    Context mContext;
    UserInfoContact.View mView;

    public UserInfoPresenter(Context context, UserInfoContact.View view) {
        mContext = context;
        mView = view;
    }
}
