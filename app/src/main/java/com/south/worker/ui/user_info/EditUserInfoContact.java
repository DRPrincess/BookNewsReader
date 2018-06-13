package com.south.worker.ui.user_info;

import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class EditUserInfoContact {

    static interface Presenter extends BasePresenter {

        void getUserInfo(int userId);
        void getEducationList();
        void setBirthTime(String time);
        void setIntoPartTime(String time);
        void setGender(int time);
        void setEducation(int time);

        void editUserInfo(int userId,int partId);



    }
    static interface View extends BaseView<Presenter> {

        void showUserInfo(UserInfoBean bean);
        void showEducationList(List<String> educationNames,  List<Integer> educationIds);
        String getName();
        String getHobby();
        String getPhone();
        String getWeixin();


    }
}
