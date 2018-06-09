package com.south.worker.ui.online_read;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.network.LoadingSubscriber;

import java.util.ArrayList;
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
            getPeopleReadRankList(type);
        }else if(type  == 1) {
            getPartReadRankList(period);
        }


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

    private void getDefaultdata() {
        List<ReadRankingBean> readRankingBeans = new ArrayList<>();

        readRankingBeans.add(new ReadRankingBean("1","王爱国",null,"285分钟"));
        readRankingBeans.add(new ReadRankingBean("2","唐柔",null,"207分钟"));
        readRankingBeans.add(new ReadRankingBean("3","陈果",null,"190分钟"));
        readRankingBeans.add(new ReadRankingBean("4","李建军",null,"163分钟"));
        readRankingBeans.add(new ReadRankingBean("5","叶霰",null,"160分钟"));
        readRankingBeans.add(new ReadRankingBean("6","甄迦",null,"104分钟"));

        mView.showData(readRankingBeans);
    }
}
