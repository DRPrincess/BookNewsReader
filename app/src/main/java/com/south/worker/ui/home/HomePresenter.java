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

    int[] imageIds = {R.drawable.show_data_news1,
            R.drawable.show_data_news2,
            R.drawable.show_data_news3,
            R.drawable.show_data_news4,
            R.drawable.show_data_news5,
            R.drawable.show_data_news6,
            R.drawable.show_data_news7,
            R.drawable.show_data_news8,
            R.drawable.show_data_news9,
            R.drawable.show_data_news10,};

    String[] urls = {
            "http://www.nanyang.gov.cn/xwzx/tpxw/281681.htm",
            "http://www.nanyang.gov.cn/xwzx/tpxw/281408.htm",
            "http://www.nanyang.gov.cn/xwzx/tpxw/280801.htm",
            "http://www.nanyang.gov.cn/xwzx/tpxw/280642.htm",
            "http://www.nanyang.gov.cn/xwzx/nyyw/277593.htm",
            "http://www.nanyang.gov.cn/xwzx/nyyw/276074.htm",
            "http://www.nanyang.gov.cn/xwzx/nyyw/274093.htm",
            "http://news.12371.cn/2018/05/31/ARTI1527753569766842.shtml",
            "http://news.12371.cn/2018/05/28/ARTI1527511342851253.shtml",
            "http://news.12371.cn/2018/05/26/ARTI1527306067114644.shtml"};

    String[] titles = {
            "城市建设 日新月异",
            "处处景观 颜值高高",
            "环库公路 穿珠成链 带动旅游 脱贫致富",
            "我市发布暴雨橙色预警",
            "省长陈润儿深入我市调研脱贫攻坚工作 市领导张文深、霍好胜参加调研",
            "邓州编外雷锋团获评全国最佳志愿服务组织",
            "我市“3·15”现场投诉咨询火爆",
            "[基层]紧跟新时代  打造好班子",
            "[发布]人民网·中国共产党新闻网推出升级版“人民党建云”平台",
            "[中央]赵乐际：坚决维护习近平总书记核心地位 维护党中央权威和集中统一领导"};

    public HomePresenter(Context context, HomeContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<NewsBean> data = new ArrayList<>();

        switch (type){
            case "0":
                for(int i = 0; i<10;i++){

                    NewsBean bean  = new NewsBean(titles[i],imageIds[i],urls[i]);
                    data.add(bean);
                }
                break;
            case "1":
            case "2":
            case "3":
                for(int i = 0; i<10;i++){

                    int j = new Random().nextInt(6);
                    NewsBean bean  = new NewsBean(titles[j],imageIds[j],urls[j]);
                    data.add(bean);
                }
                break;

        }





        mView.showData(data);
    }
}
