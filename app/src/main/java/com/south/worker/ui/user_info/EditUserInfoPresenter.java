package com.south.worker.ui.user_info;

import android.content.Context;

import com.baselib.utils.TimeUtils;
import com.south.worker.R;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.EducationBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.network.LoadingSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class EditUserInfoPresenter implements EditUserInfoContact.Presenter {

    Context mContext;
    EditUserInfoContact.View mView;

    UserInfoBean mUserInfoBean;

    public EditUserInfoPresenter(Context context, EditUserInfoContact.View view) {
        mContext = context;
        mView = view;
        mUserInfoBean = new UserInfoBean();
    }

    @Override
    public void getUserInfo(int userId) {
        UserRepository.getInstance()
                .getUserInfo(userId)
                .subscribe(new LoadingSubscriber<UserInfoBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(UserInfoBean bean) {
                        if(bean != null){
                            bean.BirthTimeString = TimeUtils.formatTimeStr(bean.BirthTimeString,"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd");
                            bean.PartyTimeString= TimeUtils.formatTimeStr(bean.PartyTimeString,"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd");
                            bean.GenderName = bean.Gender== 0?"女":"男";

                            mUserInfoBean = bean;

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
    public void editUserInfo(int userId, int partId) {
        mUserInfoBean.Id = userId;
        mUserInfoBean.BranchId = partId;
        mUserInfoBean.RealName = mView.getName();
        mUserInfoBean.Hobby = mView.getHobby();
        mUserInfoBean.Phone = mView.getPhone();
        mUserInfoBean.WeChat = mView.getWeixin();

        UserRepository.getInstance()
                .editUserInfo(mUserInfoBean)
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


    @Override
    public void getEducationList() {
        UserRepository.getInstance()
                .getEducationList()
                .subscribe(new LoadingSubscriber<List<EducationBean>>(mContext, mContext.getString(R.string.msg_loading), true) {

                    @Override
                    public void onNext(List<EducationBean> beans) {

                        List<String> names = new ArrayList<>();
                        List<Integer> ids = new ArrayList<>();

                        if (beans != null && beans.size() > 0) {

                            for (EducationBean bean :
                                    beans) {

                                names.add(bean.EducationName);
                                ids.add(bean.Id);
                            }

                        }

                        mView.showEducationList(names, ids);

                    }

                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });
    }

    @Override
    public void setBirthTime(String time) {
        mUserInfoBean.BirthTimeString = time;
    }

    @Override
    public void setIntoPartTime(String time) {
        mUserInfoBean.PartyTimeString = time;
    }

    @Override
    public void setGender(int gender) {
        mUserInfoBean.Gender = gender;
    }

    @Override
    public void setEducation(int education) {
        mUserInfoBean.EducationId = education;
    }

}
