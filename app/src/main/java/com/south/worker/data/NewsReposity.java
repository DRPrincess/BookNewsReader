package com.south.worker.data;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.RespondPageListBean;
import com.south.worker.data.network.NetHelper;
import com.south.worker.data.remote.NewsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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
    public Observable<List<NewsBean>> getNews(int pageNum, int page,int type,String searchContent) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getNews(pageNum, page,type,searchContent)
                .map(new Function<RespondPageListBean, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(RespondPageListBean bean) throws Exception {

                        List<NewsBean> datas = new ArrayList<>();
                        if(bean != null &&  bean.Result != null &&  bean.Result.Items !=null){
                            datas = new Gson().fromJson(bean.Result.Items,new TypeToken<List<NewsBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PartActivityBean>> getPartNews(int pageNum, int page,int partId,int type,String searchContent) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getPartNews(pageNum, page,partId,type,searchContent)
                .map(new Function<RespondPageListBean, List<PartActivityBean>>() {
                    @Override
                    public List<PartActivityBean> apply(RespondPageListBean bean) throws Exception {

                        List<PartActivityBean> datas = new ArrayList<>();
                        if(bean != null &&  bean.Result != null &&  bean.Result.Items !=null){
                            datas = new Gson().fromJson(bean.Result.Items,new TypeToken<List<PartActivityBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BannerBean>> getNewsBanner() {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getNewsBanner()
                .map(new Function<RespondBean, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> apply(RespondBean bean) throws Exception {

                        List<BannerBean> datas = new ArrayList<>();
                        if(bean != null && bean.Result != null){
                            datas = new Gson().fromJson(bean.Result,new TypeToken<List<BannerBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BannerBean>> getPartBanner(int partId) {
        return NetHelper.createService(NewsRemoteDataSource.class)
                .getPartBanner(partId)
                .map(new Function<RespondBean, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> apply(RespondBean bean) throws Exception {

                        List<BannerBean> datas = new ArrayList<>();
                        if(bean != null && bean.Result != null){
                            datas = new Gson().fromJson(bean.Result,new TypeToken<List<BannerBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
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
