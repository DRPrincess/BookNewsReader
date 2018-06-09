package com.south.worker.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.baselib.utils.SharedPreferencesUtil;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.data.NewsReposity;
import com.south.worker.data.UserRepository;
import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.LoadingSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @NonNull
    private ArrayList<NewsBean> getDefaultNewsBeans(String type) {


        int[] imageIds = {R.drawable.show_data_news_weixin1,
                R.drawable.show_data_news_weixin2,
                R.drawable.show_data_news_weixin3,
                R.drawable.show_data_news_weixin4,
                R.drawable.show_data_news_weixin5,
                R.drawable.show_data_news_weixin6,
                R.drawable.show_data_news_weixin7,
                R.drawable.show_data_news_weixin8,
                R.drawable.show_data_news_weixin9,
                R.drawable.show_data_news_weixin10,
                R.drawable.show_data_news_weixin11,
                R.drawable.show_data_news_weixin12,
                R.drawable.show_data_news_weixin13,
                R.drawable.show_data_news_weixin14
        };


        String[] urls = {
                "https://mp.weixin.qq.com/s/zewcR8nURlXHYCtjBgBGaQ",
                "https://mp.weixin.qq.com/s/U5g4AMf0z0U9yNIoA28QGw",
                "https://mp.weixin.qq.com/s/mpLY4p4yrTMm5CALUkajQg",
                "https://mp.weixin.qq.com/s/_zH6d6VwIwwabXVEQPpYgw",
                "https://mp.weixin.qq.com/s/qlY5--XIZt9T_x46qQHzxQ",
                "https://mp.weixin.qq.com/s/rpd0OsVs2usO0-1cL4t77A",
                "https://mp.weixin.qq.com/s/Ca50q6yUPWRv3z62GycWFw",
                "https://mp.weixin.qq.com/s/aXFOVBPbkt82xIPxyi_7KQ",
                "https://mp.weixin.qq.com/s/gftEL8LYwrV5zv05lOUBaQ",
                "https://mp.weixin.qq.com/s/XEb_YB0SbEKbopPNb3sa2Q",
                "https://mp.weixin.qq.com/s/MeZpX-3X-wr4gokRDVOW8g",
                "https://mp.weixin.qq.com/s/xMP3u9ETfiGE3EgvMhKIrw",
                "https://mp.weixin.qq.com/s/8PFEc7QD15zz8Gm-aap14A",
                "https://mp.weixin.qq.com/s/MmKP9EWPA8lhEvGUaKotew"
        };

        String[] titles = {
                "【一线风采】夏夜施工忙",
                "【南工故事】高铁时代即将来临 人员提前介入保安全",
                " 安全生产月丨一组漫画送给你，轻松接受安全知识",
                "【六一“铁娃”说爸妈】“六一”啦！铁爸铁妈们，这里有一群“铁娃”的留言，请注意查收！",
                "【南工故事】你的平安，我的责任",
                "【企业文化三年工程·传统传承】一声汽笛 鸣奏百年交响",
                "【一线风采】风里雨里 线路上等你",
                "【干部课堂】党员干部应知名词100解",
                "【聚焦媒体】南阳工务段：“集中修”紧张进行时",
                "【集中修特刊】穿行在深山的集中修“战车”",
                "【集中修特刊】天气很“任性”，他们从不“Care”",
                "【集中修特刊】穿行在深山的集中修“战车”",
                "新时代·铁路榜样｜徐前凯：5秒钟的决定，拯救一条生命！",
                "【南工风采】每一个铁路人都是\"特种兵\"",
                "【聚焦媒体】中原男儿好气势！ “集中修战队”顺利“起航”"};

        ArrayList<NewsBean> data = new ArrayList<>();

        switch (type){
            case "0":
                for(int i = 0; i<4;i++){

                    NewsBean bean  = new NewsBean(titles[i],imageIds[i],urls[i]);
                    data.add(bean);
                }
                break;
            case "1":
                for(int i = 4; i<8;i++){

                    NewsBean bean  = new NewsBean(titles[i],imageIds[i],urls[i]);
                    data.add(bean);
                }
                break;
            case "2":
                for(int i = 8; i<12;i++){

                    NewsBean bean  = new NewsBean(titles[i],imageIds[i],urls[i]);
                    data.add(bean);
                }
                break;
            case "3":
                for(int i = 12; i<14;i++){

                    NewsBean bean  = new NewsBean(titles[i],imageIds[i],urls[i]);
                    data.add(bean);
                }
                break;

        }
        return data;
    }

    @Override
    public void getData(int page, int pageNum, int type, String searchContent) {

        NewsReposity.getInstance()
                .getNews(pageNum,page,type)
                .subscribe(new LoadingSubscriber<List<NewsBean>>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(List<NewsBean> beanList) {
                        mView.showData(beanList);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });



    }



    @Override
    public void getNewsUrl(int newsId) {
        NewsReposity.getInstance()
                .getNewsUrl(newsId)
                .subscribe(new LoadingSubscriber<NewUrlBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(NewUrlBean bean) {
                        mView.startWebActivity("","");

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });
    }

    @Override
    public void getBanner() {
        NewsReposity.getInstance()
                .getNewsBanner()
                .subscribe(new LoadingSubscriber<List<BannerBean>>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {

                        List<String> imgUrls  = new ArrayList<>();
                        List<String> titles  = new ArrayList<>();
                        List<String> linkUrls = new ArrayList<>();

                        mView.showBanner(imgUrls,titles,linkUrls);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }
}
