package com.south.worker.ui.user_info;

import android.content.Context;
import android.util.Log;

import com.baselib.utils.SharedPreferencesUtil;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.network.LoadingSubscriber;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.PREFIX;


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
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_PART_ID,bean.BranchId);
                            SharedPreferencesUtil.saveData(mContext, SharedPreferencesConfig.SHARED_KEY_USER_NAME,bean.RealName);

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

    @Override
    public void uploadHeadImg(int userId, String imagePath) {

        UserRepository.getInstance()
                .uploadAvatar(userId,imagePath)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_uploading),true) {
                    @Override
                    public void onSubscriberError(String errorMsg) {

                    }

                    @Override
                    public void onNext(RespondBean bean) {
                        mView.showTipDialog(bean.Msg);
                    }
                });








    }
}
