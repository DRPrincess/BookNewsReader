package com.south.worker.ui.home;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;

import java.util.ArrayList;
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class HomePresenter implements HomeContact.Presenter {

    Context mContext;
    HomeContact.View mView;

    int[] imageIds = {R.drawable.user_show_data5,
            R.drawable.user_show_data6,
            R.drawable.user_show_data7,};

    String[] urls = {"http://news.12371.cn/2018/05/31/ARTI1527753569766842.shtml",
            "http://news.12371.cn/2018/05/28/ARTI1527511342851253.shtml",
            "http://news.12371.cn/2018/05/26/ARTI1527306067114644.shtml",};

    String[] titles = {"[基层]紧跟新时代  打造好班子",
            "[发布]人民网·中国共产党新闻网推出升级版“人民党建云”平台",
            "[中央]赵乐际：坚决维护习近平总书记核心地位 维护党中央权威和集中统一领导"};

    public HomePresenter(Context context, HomeContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<NewsBean> data = new ArrayList<>();

        NewsBean bean = null;
//        switch(type){
//
//            case "0":
//                break;
//            case "1":
//                bean = new NewsBean(titles[new Random().nextInt(3)],imageIds[new Random().nextInt(3)],urls[new Random().nextInt(3)]);
//                break;
//            case "2":
//                bean = new NewsBean(titles[new Random().nextInt(3)],imageIds[new Random().nextInt(3)],urls[new Random().nextInt(3)]);
//                break;
//            case "3":
//                bean = new NewsBean(titles[new Random().nextInt(3)],imageIds[new Random().nextInt(3)],urls[new Random().nextInt(3)]);
//                break;
//
//        }

        for(int i = 0; i<10;i++){

            bean = new NewsBean(titles[new Random().nextInt(3)],imageIds[new Random().nextInt(3)],urls[new Random().nextInt(3)]);
            data.add(bean);
        }

        mView.showData(data);
    }
}
