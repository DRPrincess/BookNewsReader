package com.south.worker.ui.online_read;

import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.ReadBookTimeBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadContact {

    public  static interface Presenter extends BasePresenter {
        void getOnlineBook(int page, int pageNum,String searchContent);

        void getMyReadRecord(int userId, int page, int pageNum, String searchContent);

        void getMyThinking(int userId, int page, int pageNum, String searchContent);

        void addReadBook(ReadBookTimeBean bookTimeBean);

        void addMyBook(int userId, int bookId, String bookName, String url);

        void likeReadThinking( int userId,  int thinkingid, int num);

    }
    public static interface View extends BaseView<Presenter> {
        void showOnlineBookList(List<OnlineReadBean> newsBeans);

        void showMyReadRecordList(List<OnlineReadBean> newsBeans);

        void showMyThinkingList(List<OnlineReadBean> newsBeans);

        void startWebActivity(String title, String url,int bookId,int type);

    }


    public static interface AllBookView extends BaseView<Presenter> {
        void showOnlineBookList(List<OnlineBookBean> newsBeans);
    }
}
