package com.south.worker.data;

import com.south.worker.data.bean.EducationBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.bean.UserLoginBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public interface UserDataSource  {


    Observable<UserLoginBean> login(String userName,String password);
    Observable<UserInfoBean> getUserInfo(int userId);
    Observable<RespondBean> editUserInfo(UserInfoBean bean);
    Observable<RespondBean> changePassword(int userId,String password,String newPassword);
    Observable<List<EducationBean>> getEducationList();
    Observable<RespondBean> editSign(int userId,String signStr);
    Observable<RespondBean> uploadAvatar(int userId,String imageFilePath);
}
