package com.south.worker.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.ReadBookTimeBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.RespondPageListBean;
import com.south.worker.data.network.NetHelper;
import com.south.worker.data.remote.BookRemoteDataSource;

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

public class BookReposity implements BookDataSource{

    private static BookReposity instance;

    private BookReposity() {

    }

    public static BookReposity getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new BookReposity();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<List<OnlineBookBean>> getAllBooks(int pageNum, int page,String searchContent) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getAllBooks(pageNum,page,searchContent)
                .map(new Function<RespondPageListBean, List<OnlineBookBean>>() {
                    @Override
                    public List<OnlineBookBean> apply(RespondPageListBean bean) throws Exception {

                        List<OnlineBookBean> datas = new ArrayList<>();
                        if(bean != null &&  bean.Result != null &&  bean.Result.Items !=null){
                            datas = new Gson().fromJson(bean.Result.Items,new TypeToken<List<OnlineBookBean>>(){}.getType());

                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MyBookBean>> getMyBooks(int userId,int pageNum, int page,String searchContent) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getMyBooks(userId,pageNum,page,searchContent)
                .map(new Function<RespondPageListBean, List<MyBookBean>>() {
                    @Override
                    public List<MyBookBean> apply(RespondPageListBean bean) throws Exception {

                        List<MyBookBean> datas = new ArrayList<>();
                        if(bean != null &&  bean.Result != null &&  bean.Result.Items !=null){
                            datas = new Gson().fromJson(bean.Result.Items,new TypeToken<List<MyBookBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ReadThinkingBean>> getMyReadThinkings(int userId,int pageNum, int page) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getMyReadThinkings(userId,pageNum,page)
                .map(new Function<RespondBean, List<ReadThinkingBean>>() {
                    @Override
                    public List<ReadThinkingBean> apply(RespondBean bean) throws Exception {

                        List<ReadThinkingBean> datas = new ArrayList<>();
                        if(bean != null &&  bean.item != null){
                            datas = new Gson().fromJson(bean.item,new TypeToken<List<ReadThinkingBean>>(){}.getType());

                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ReadRankingBean>> getPeopleReadRankList(int timePeriod) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getPeopleReadRankList(timePeriod)
                .map(new Function<RespondBean, List<ReadRankingBean>>() {
                    @Override
                    public List<ReadRankingBean> apply(RespondBean bean) throws Exception {

                        List<ReadRankingBean> datas = new ArrayList<>();
                        if(bean != null && bean.Data != null){
                            datas = new Gson().fromJson(bean.Data,new TypeToken<List<ReadRankingBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ReadRankingBean>> getPartReadRankList(int timePeriod) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getPartReadRankList(timePeriod)
                .map(new Function<RespondBean, List<ReadRankingBean>>() {
                    @Override
                    public List<ReadRankingBean> apply(RespondBean bean) throws Exception {

                        List<ReadRankingBean> datas = new ArrayList<>();
                        if(bean != null && bean.Data != null){
                            datas = new Gson().fromJson(bean.Data,new TypeToken<List<ReadRankingBean>>(){}.getType());
                        }

                        return datas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> addReadBook(ReadBookTimeBean bookTimeBean) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .addReadBook(bookTimeBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ReadRankingBean> getMyPartReadRankList(int id, int timePeriod) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getMyPartReadRankList(timePeriod,id)
                .map(new Function<RespondBean, ReadRankingBean>() {
                    @Override
                    public ReadRankingBean apply(RespondBean bean) throws Exception {
                        ReadRankingBean readRankingBean = new ReadRankingBean();
                        if(bean != null && bean.Data != null){
                            List<ReadRankingBean> datas = new Gson().fromJson(bean.Data,new TypeToken<List<ReadRankingBean>>(){}.getType());

                            if(datas != null && datas.size() >0){
                                readRankingBean = datas.get(0);
                            }
                        }

                        return readRankingBean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ReadRankingBean> getMyReadRankList(int id, int timePeriod) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .getMyReadRankList(id,timePeriod)
                .map(new Function<RespondBean, ReadRankingBean>() {
                    @Override
                    public ReadRankingBean apply(RespondBean bean) throws Exception {
                        ReadRankingBean readRankingBean = new ReadRankingBean();
                        if(bean != null && bean.Data != null){
                            List<ReadRankingBean> datas = new Gson().fromJson(bean.Data,new TypeToken<List<ReadRankingBean>>(){}.getType());

                            if(datas != null && datas.size() >0){
                                readRankingBean = datas.get(0);
                            }
                        }

                        return readRankingBean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<RespondBean> addMyBook(int userId, int bookId, String bookName) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .addMyBook(userId,bookId,bookName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> deleteMyBook(int userId, int bookId, String bookName) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .deleteMyBook(userId,bookId,bookName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> addReadThinking(ReadThinkingBean bean) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .addReadThinking(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RespondBean> likeReadThinking(int userId, int thinkingid, int num) {
        return NetHelper.createService(BookRemoteDataSource.class)
                .likeReadThinking(userId,thinkingid,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
