package com.south.worker.ui.home;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;

import java.util.ArrayList;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class HomePresenter implements HomeContact.Presenter {

    Context mContext;
    HomeContact.View mView;


    public HomePresenter(Context context, HomeContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<NewsBean> data = new ArrayList<>();

        NewsBean bean = null;
        switch(type){

            case "0":
                bean = new NewsBean("信息速递-4月27日，中央和国家机关工委召开中央和国家机关各单位机关党委主要负责人会议", R.drawable.user_show_data5,"");
                break;
            case "1":
                bean = new NewsBean("通知公告-4月27日，中央和国家机关工委召开中央和国家机关各单位机关党委主要负责人会议\"-4月27日，中央和国家机关工委召开中央和国家机关各单位机关党委主要负责人会议", R.drawable.user_show_data6,"");
                break;
            case "2":
                bean = new NewsBean("党内活动-4月27日，中央和国家机关工委召开中央和国家机关各单位机关党委主要负责人会议", R.drawable.user_show_data7,"");
                break;
            case "3":
                bean = new NewsBean("在线党课-4月27日，中央和国家机关工委召开中央和国家机关各单位机关党委主要负责人会议", R.drawable.user_show_data6,"");
                break;

        }

        for(int i = 0; i<10;i++){
            data.add(bean);
        }


        mView.showData(data);
    }
}
