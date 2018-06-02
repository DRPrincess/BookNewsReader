package com.south.worker.ui.online_read;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.ui.user_info.UserInfoContact;

import java.util.ArrayList;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadPresenter implements OnlineReadContact.Presenter {

    Context mContext;
    OnlineReadContact.View mView;

    public OnlineReadPresenter(Context context, OnlineReadContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getOnlineBook(int page, int pageNum, String searchContent) {

        ArrayList<OnlineReadBean> data = new ArrayList<>();

        OnlineBookBean bookBean = new OnlineBookBean(R.drawable.user_show_data1,"http://www.12371.cn/special/blqs/xjptzglz/");

        for (int i = 0; i < 10; i++) {
            OnlineReadBean onlineReadBean = new OnlineReadBean("0",bookBean);
            data.add(onlineReadBean);
        }

        mView.showOnlineBookList(data);

    }

    @Override
    public void getMyOnlineBook(int page, int pageNum, String searchContent) {
        ArrayList<OnlineReadBean> data = new ArrayList<>();

        OnlineBookBean bookBean = new OnlineBookBean(R.drawable.user_show_data1,"http://www.12371.cn/special/blqs/xjptzglz/");

        for (int i = 0; i < 4; i++) {
            OnlineReadBean onlineReadBean = new OnlineReadBean("0",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyOnlineBookList(data);
    }

    @Override
    public void getMyReadRecord(int page, int pageNum, String searchContent) {

        ArrayList<OnlineReadBean> data = new ArrayList<>();

        MyBookBean bookBean = new MyBookBean(R.drawable.user_show_data1,"3","264","http://www.12371.cn/special/blqs/xjptzglz/");

        for (int i = 0; i < 4; i++) {
            OnlineReadBean onlineReadBean = new OnlineReadBean("1",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyReadRecordList(data);
    }

    @Override
    public void getMyThinking(int page, int pageNum, String searchContent) {
        ArrayList<OnlineReadBean> data = new ArrayList<>();

        ReadThinkingBean bookBean = new ReadThinkingBean("xx读后感"," 习近平作为中国党和国家的最高领导人，围绕治国理政发表了大量讲话，提出了许多新思想、新观点、新论断，深刻回答了新的历史条件下党和国家发展的重大理论和现实问题");

        for (int i = 0; i < 10; i++) {
            OnlineReadBean onlineReadBean = new OnlineReadBean("2",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyThinkingList(data);
    }
}
