package com.south.worker.ui.user_info;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.network.LoadingSubscriber;

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

    @Override
    public void getUserInfo(int userId) {
        UserRepository.getInstance()
                .getUserInfo(userId)
                .subscribe(new LoadingSubscriber<UserInfoBean>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(UserInfoBean bean) {

                        if(bean != null){
                            bean.GenderName = bean.Gender== 0?"女":"男";
                            mView.showUserInfo(bean);
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });

    }
}
