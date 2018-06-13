package com.south.worker.data.remote;

import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.RespondPageListBean;
import com.south.worker.data.bean.UserLoginBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/8.
 */

public interface NewsRemoteDataSource {

    //首页新闻
    @FormUrlEncoded
    @POST("NewsApi/List")
    Observable<RespondPageListBean> getNews(@Field("NumPerPage") int pageNum, @Field("PageNum") int page, @Field("TypeId") int Id, @Field("name") String name);

    //支部新闻
    @FormUrlEncoded
    @POST("NewsApi/BranchsList")
    Observable<RespondPageListBean> getPartNews(@Field("NumPerPage") int pageNum, @Field("PageNum") int page,@Field("Id") int partId,@Field("TypeId") int type,@Field("name") String name);

    //首页banner
    @POST("NewsApi/BannerList")
    Observable<RespondBean> getNewsBanner();
    @FormUrlEncoded
    //支部Banner
    @POST("NewsApi/BranchsBanner")
    Observable<RespondBean> getPartBanner(@Field("Id") int Id);

    //查看新闻
    @FormUrlEncoded
    @POST("NewsApi/LookNews")
    Observable<NewUrlBean> getNewsUrl(@Field("Id") int newsId);
}
