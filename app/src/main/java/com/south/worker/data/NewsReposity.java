package com.south.worker.data;


import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.network.NetHelper;
import com.south.worker.data.remote.NewsRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/9.
 */

public class NewsReposity implements NewsDataSource{

    private static NewsReposity instance;

    private NewsReposity() {

    }

    public static NewsReposity getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new NewsReposity();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<List<NewsBean>> getNews(int pageNum, int page,int type) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getNews(pageNum, page,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PartActivityBean>> getPartNews(int pageNum, int page,int type) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getPartNews(pageNum, page,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BannerBean>> getNewsBanner() {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getNewsBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BannerBean>> getPartBanner(int partId) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getPartBanner(partId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<NewUrlBean> getNewsUrl(int newsId) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getNewsUrl(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
