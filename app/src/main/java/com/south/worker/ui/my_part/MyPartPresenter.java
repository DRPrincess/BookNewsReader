package com.south.worker.ui.my_part;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.home.HomeContact;

import java.util.ArrayList;
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartPresenter implements MyPartContact.Presenter {

    Context mContext;
    MyPartContact.View mView;


    int[] imageIds = {R.drawable.show_data_part1,
            R.drawable.show_data_part2,
            R.drawable.show_data_part3};

    String[] urls = {
            "http://news.12371.cn/2018/05/31/ARTI1527753569766842.shtml",
            "http://news.12371.cn/2018/05/28/ARTI1527511342851253.shtml",
            "http://news.12371.cn/2018/05/26/ARTI1527306067114644.shtml"};

    String[] titles = {
            "[基层]紧跟新时代  打造好班子",
            "[发布]人民网·中国共产党新闻网推出升级版“人民党建云”平台",
            "[中央]赵乐际：坚决维护习近平总书记核心地位 维护党中央权威和集中统一领导"};

    String[] contents = {
            "中国共产党新闻网北京5月7日电 为满足广大基层党组织的应用需求，更好地服务基层党建工…",
            "贯彻落实《中央巡视工作规划（2018—2022年）》,推进会14日在成都召开，中共中央政治局常...",
            "人民网·中国共产党新闻网推出升级版“人民党建云”平台,服务任命服务党"};

    public MyPartPresenter(Context context, MyPartContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<PartActivityBean> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            PartActivityBean bean = new PartActivityBean(titles[new Random().nextInt(2)],
                    contents[new Random().nextInt(2)],
                    imageIds[new Random().nextInt(2)], urls[new Random().nextInt(2)]);
            data.add(bean);
        }

        mView.showData(data);
    }
}
