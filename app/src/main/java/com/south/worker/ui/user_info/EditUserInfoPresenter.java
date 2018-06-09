package com.south.worker.ui.user_info;

import android.content.Context;

import com.baselib.utils.SharedPreferencesUtil;
import com.baselib.utils.TimeUtils;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.LoadingSubscriber;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class EditUserInfoPresenter implements EditUserInfoContact.Presenter {

    Context mContext;
    EditUserInfoContact.View mView;

    public EditUserInfoPresenter(Context context, EditUserInfoContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getUserInfo(int userId) {
        UserRepository.getInstance()
                .getUserInfo(userId)
                .subscribe(new LoadingSubscriber<UserInfoBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(UserInfoBean bean) {



                        if(bean != null){
                            bean.BirthTime = TimeUtils.transformDateToJavaTime(bean.BirthTime);
                            bean.PartyTime = TimeUtils.transformDateToJavaTime(bean.PartyTime);
                            mView.showUserInfo(bean);
                        }


                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });

    }

    @Override
    public void editUserInfo(UserInfoBean bean) {
        UserRepository.getInstance()
                .editUserInfo(bean)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean bean) {

                        mView.showTipDialog(bean.Msg);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });
    }
}
