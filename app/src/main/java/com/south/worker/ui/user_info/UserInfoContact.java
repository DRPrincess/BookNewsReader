package com.south.worker.ui.user_info;

import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;
import com.south.worker.ui.online_read.RankingListContact;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class UserInfoContact {

    static interface Presenter extends BasePresenter {

        void getUserInfo(int userId);

        void uploadHeadImg(int userId,String imagePath);


    }
    static interface View extends BaseView<Presenter> {

        void showUserInfo(UserInfoBean bean);



    }
}
