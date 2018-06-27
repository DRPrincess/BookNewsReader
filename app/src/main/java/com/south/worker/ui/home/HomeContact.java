package com.south.worker.ui.home;

import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;
import com.south.worker.ui.login.LoginContact;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class HomeContact {


    static interface Presenter extends BasePresenter {

        void getData(int page,int pageNum,int type,String searchContent);
        void getNewsUrl(int newsId);
        void getBanner();

    }
    static interface View extends BaseView<Presenter> {

        void showData(List<NewsBean> newsBeans);
        void startWebActivity(String title,String url);
        void showBanner(List<String> imageUrl,List<BannerBean> bannerBeans);
        void noData();
    }
}
