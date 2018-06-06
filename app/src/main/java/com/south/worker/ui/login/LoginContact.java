package com.south.worker.ui.login;

import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class LoginContact {

    static interface Presenter extends BasePresenter {

        void login(String userName,String userPassword);

    }
    static interface View extends BaseView<Presenter> {

        void goHome();
        boolean isRememberPassword();
    }
}
