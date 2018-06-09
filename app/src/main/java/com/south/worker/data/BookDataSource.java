package com.south.worker.data;

import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/9.
 */

public interface BookDataSource {


    //图书列表
    Observable<List<OnlineBookBean>> getAllBooks( int pageNum,int page);

    //查看收藏列表
    Observable<List<MyBookBean>> getMyBooks( int pageNum, int page);

    //读书心得列表
    Observable<List<ReadThinkingBean>> getMyReadThinkings(int pageNum,  int page);


    //获取用户阅读排行
    //TypeId   0是一周 1是一个月 2是三个月
    Observable<List<ReadRankingBean>> getPeopleReadRankList(int timePeriod);

    //获取支部阅读排行
    Observable<List<ReadRankingBean>> getPartReadRankList(int timePeriod);

    //添加阅读时长
    //UserId, LengthofReadingTime阅读时长 BookId图书Id TodayTime DateTime时间格式要求传入时间只传今天的时间 不带时分秒
    Observable<RespondBean> addReadBook( int useId, int bookId,String totalTime);


    //添加收藏
    Observable<RespondBean> addMyBook( int userId,  int bookId, String bookName);

    //删除收藏
    Observable<RespondBean> deleteMyBook( int userId,int bookId,String bookName);


    //读书心得添加
    Observable<RespondBean> addReadThinking(@Body ReadThinkingBean bean);

}
