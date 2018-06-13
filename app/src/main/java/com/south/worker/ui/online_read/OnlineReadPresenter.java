package com.south.worker.ui.online_read;

import android.content.Context;
import android.widget.Toast;

import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.NewsReposity;
import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.ReadBookTimeBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.user_info.UserInfoContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadPresenter implements OnlineReadContact.Presenter {

    Context mContext;
    OnlineReadContact.View mView;
    OnlineReadContact.AllBookView mAllBookView;


    public OnlineReadPresenter(Context context, OnlineReadContact.View view) {
        mContext = context;
        mView = view;
    }

    public OnlineReadPresenter(Context context, OnlineReadContact.AllBookView view) {
        mContext = context;
        mAllBookView = view;
    }


    @Override
    public void getOnlineBook(int page, int pageNum, String searchContent) {
        BookReposity.getInstance()
                .getAllBooks(pageNum,page,searchContent)
                .subscribe(new LoadingSubscriber<List<OnlineBookBean>>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(List<OnlineBookBean> bookBeans) {


                        if(mAllBookView != null){
                            mAllBookView.showOnlineBookList(bookBeans);
                            return;
                        }


                        ArrayList<OnlineReadBean> data = new ArrayList<>();

                        if (bookBeans != null){
                            for (int i = 0; i < bookBeans.size(); i++) {
                                OnlineReadBean onlineReadBean = new OnlineReadBean("0",bookBeans.get(i));
                                data.add(onlineReadBean);
                            }
                        }

                        mView.showOnlineBookList(data);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });

    }



    @Override
    public void getMyReadRecord(int userId,int page, int pageNum, String searchContent) {
        BookReposity.getInstance()
                .getMyBooks(userId,pageNum,page,searchContent)
                .subscribe(new LoadingSubscriber<List<MyBookBean>>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(List<MyBookBean> bookBeans) {

                        ArrayList<OnlineReadBean> data = new ArrayList<>();

                        if (bookBeans != null){
                            for (int i = 0; i < bookBeans.size(); i++) {
                                OnlineReadBean onlineReadBean = new OnlineReadBean("1",bookBeans.get(i));
                                data.add(onlineReadBean);
                            }
                        }

                        mView.showOnlineBookList(data);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });

    }

    @Override
    public void getMyThinking(int userId,int page, int pageNum, String searchContent) {
        BookReposity.getInstance()
                .getMyReadThinkings(userId,pageNum,page)
                .subscribe(new LoadingSubscriber<List<ReadThinkingBean>>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(List<ReadThinkingBean> bookBeans) {

                        ArrayList<OnlineReadBean> data = new ArrayList<>();

                        if (bookBeans != null){
                            for (int i = 0; i < bookBeans.size(); i++) {
                                OnlineReadBean onlineReadBean = new OnlineReadBean("2",bookBeans.get(i));
                                data.add(onlineReadBean);
                            }
                        }

                        mView.showOnlineBookList(data);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }

    @Override
    public void addReadBook(final ReadBookTimeBean bookTimeBean) {
        BookReposity.getInstance()
                .addReadBook(bookTimeBean)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                        if (respondBean.IsOk){
                            mView.showTipDialog("此次阅读时间为"+ bookTimeBean.LengthofReadingTime +"分钟");
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showTipDialog(errorMsg);
                    }

                });
    }

    @Override
    public void addMyBook(int userId, final int bookId, final String bookName, final String url) {
        BookReposity.getInstance()
                .addMyBook(userId,bookId,bookName)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                        mView.startWebActivity(bookName,url,bookId,0);
                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showTipDialog(errorMsg);
                    }

                });
    }

    @Override
    public void likeReadThinking(int userId, int thinkingid, int num) {
        BookReposity.getInstance()
                .likeReadThinking(userId,thinkingid,num)
                .subscribe(new LoadingSubscriber<RespondBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                        mView.showTipDialog(respondBean.Msg);
                        if(respondBean.IsOk){
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showTipDialog(errorMsg);
                    }

                });
    }


}
