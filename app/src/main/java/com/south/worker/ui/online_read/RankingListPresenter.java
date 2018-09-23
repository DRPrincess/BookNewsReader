package com.south.worker.ui.online_read;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.network.LoadingSubscriber;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class RankingListPresenter  implements RankingListContact.Presenter{
    Context mContext;
    RankingListContact.View mView;


    public RankingListPresenter(Context context, RankingListContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int type, int period) {

        if(type == 0){
            getPeopleReadRankList(period);
        }else if(type  == 1) {
            getPartReadRankList(period);
        }


    }

    @Override
    public void getMyData(int userId, int period) {
        BookReposity.getInstance()
                .getMyReadRankList(userId,period)
                .subscribe(new LoadingSubscriber<ReadRankingBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(ReadRankingBean bean) {
                        if (bean != null){
                            mView.showRank(bean.readTime,bean.Num ,bean.userName);
                        }
                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }

    @Override
    public void getMyPartData(int partId, int period) {
        BookReposity.getInstance()
                .getMyPartReadRankList(partId,period)
                .subscribe(new LoadingSubscriber<ReadRankingBean>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(ReadRankingBean bean) {

                        if (bean != null){
                            mView.showRank(bean.readTime,bean.Num, bean.userName);
                        }

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }

    private void getPartReadRankList(int period){
        BookReposity.getInstance()
                .getPartReadRankList(period)
                .subscribe(new LoadingSubscriber<List<ReadRankingBean>>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(List<ReadRankingBean> readRankingBeans) {

                        mView.showData(readRankingBeans);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }

    private void getPeopleReadRankList(int peroid){
        BookReposity.getInstance()
                .getPeopleReadRankList(peroid)
                .subscribe(new LoadingSubscriber<List<ReadRankingBean>>(mContext,mContext.getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(List<ReadRankingBean> readRankingBeans) {

                        mView.showData(readRankingBeans);

                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                });
    }

}
