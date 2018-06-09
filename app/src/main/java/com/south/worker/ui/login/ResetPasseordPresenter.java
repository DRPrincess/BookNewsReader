package com.south.worker.ui.login;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.network.LoadingSubscriber;

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

    @Override
    public void changePassord(int userId, String password, String newPassword) {
        UserRepository.getInstance()
                .changePassword(userId,password,newPassword)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean bean) {

                        if (bean.IsOk){
                            mView.showToast(bean.Msg);
                            mView.LoginAgain();
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showTipDialog(errorMsg);
                    }


                });
    }
}
