package com.south.worker.ui.my_part;

import android.content.Context;
import android.support.annotation.NonNull;

import com.south.worker.R;
import com.south.worker.constant.NetConfig;
import com.south.worker.data.NewsReposity;
import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.home.HomeContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartPresenter implements MyPartContact.Presenter {

    Context mContext;
    MyPartContact.View mView;



    public MyPartPresenter(Context context, MyPartContact.View view) {
        mContext = context;
        mView = view;
    }


    @Override
    public void getData(int page, int pageNum, int partId,int type, String searchContent) {

        NewsReposity.getInstance()
                .getPartNews(pageNum,page,partId,type,searchContent)
                .subscribe(new LoadingSubscriber<List<PartActivityBean>>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(List<PartActivityBean> beanList) {
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
                        mView.startWebActivity(bean.Title,bean.Content);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }


                });
    }

    @Override
    public void getBanner(int partId) {
        NewsReposity.getInstance()
                .getPartBanner(partId)
                .subscribe(new LoadingSubscriber<List<BannerBean>>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {

                        List<String> imgUrls  = new ArrayList<>();

                        if(bannerBeans != null && bannerBeans.size() >0){
                            for (BannerBean bean: bannerBeans) {
                                imgUrls.add(NetConfig.IMAGE_PREFIXX+bean.Pic);
                            }
                        }

                        mView.showBanner(imgUrls,bannerBeans);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }
}
