package com.south.worker.ui.user_info;

import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class EditUserInfoContact {

    static interface Presenter extends BasePresenter {

        void getUserInfo(int userId);
        void editUserInfo(UserInfoBean bean);


    }
    static interface View extends BaseView<Presenter> {

        void showUserInfo(UserInfoBean bean);

    }
}
