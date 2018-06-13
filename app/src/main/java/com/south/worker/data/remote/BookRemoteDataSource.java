package com.south.worker.data.remote;

import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.ReadBookTimeBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.RespondPageListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/8.
 */

public interface BookRemoteDataSource {

    //图书列表
    @FormUrlEncoded
    @POST("BookApi/List")
    Observable<RespondPageListBean> getAllBooks(@Field("NumPerPage") int pageNum, @Field("PageNum") int page, @Field("name")String searchContent);

    //查看收藏列表
    @FormUrlEncoded
    @POST("BookApi/CollectionList")
    Observable<RespondPageListBean> getMyBooks(@Field("Id") int id,@Field("NumPerPage") int pageNum, @Field("PageNum") int page,@Field("name")String searchContent);

    //读书心得列表
    @FormUrlEncoded
    @POST("BookHeart/List")
    Observable<RespondBean> getMyReadThinkings(@Field("Id") int id,@Field("NumPerPage") int pageNum, @Field("PageNum") int page);


    //获取用户阅读排行
    //TypeId   0是一周 1是一个月 2是三个月
    @FormUrlEncoded
    @POST("LengthofReading/UserList")
    Observable<RespondBean> getPeopleReadRankList(@Field("TypeId") int timePeriod);

    //获取支部阅读排行
    @FormUrlEncoded
    @POST("LengthofReading/BranchList")
    Observable<RespondBean> getPartReadRankList(@Field("TypeId") int timePeriod);


    //获取支部阅读排行
    @FormUrlEncoded
    @POST("LengthofReading/BranchRead?")
    Observable<RespondBean> getMyPartReadRankList(@Field("TypeId") int timePeriod,@Field("Id") int id);


    //获取支部阅读排行
    @FormUrlEncoded
    @POST("LengthofReading/UserRead?")
    Observable<RespondBean> getMyReadRankList(@Field("Id") int id,@Field("TypeId") int timePeriod);



    //添加阅读时长
    //UserId, LengthofReadingTime阅读时长 BookId图书Id TodayTime DateTime时间格式要求传入时间只传今天的时间 不带时分秒
    @POST("LengthofReading/AddReading")
    Observable<RespondBean> addReadBook(@Body ReadBookTimeBean bookTimeBean);


    //添加收藏
    @FormUrlEncoded
    @POST("BookApi/Add")
    Observable<RespondBean> addMyBook(@Field("UserId") int userId,@Field("BookId") int bookId,@Field("BookName") String bookName);

    //删除收藏
    @FormUrlEncoded
    @POST("BookApi/Delete")
    Observable<RespondBean> deleteMyBook(@Field("UserId") int userId,@Field("BookId") int bookId,@Field("BookName") String bookName);


   //读书心得添加
    @POST("BookHeart/Add")
    Observable<RespondBean> addReadThinking(@Body ReadThinkingBean bean);

    //点赞
    @FormUrlEncoded
    @POST("BookApi/AddPraise")
    Observable<RespondBean> likeReadThinking(@Field("UserId") int userId,@Field("BookHeartId") int thinkingId,@Field("Num") int num);


}
