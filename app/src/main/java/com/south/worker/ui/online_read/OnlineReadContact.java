package com.south.worker.ui.online_read;

import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadContact {

    static interface Presenter extends BasePresenter {
        void getOnlineBook(int page, int pageNum,String searchContent);
        void getMyReadRecord(int page, int pageNum,String searchContent);
        void getMyThinking(int page, int pageNum,String searchContent);
         //DateTime时间格式要求传入时间只传今天的时间
        void addReadBook(int useId, int bookId,String totalTime);
        void addMyBook(int userId,  int bookId, String bookName);
        void deleteMyBook(int userId,int bookId,String bookName);

    }
    static interface View extends BaseView<Presenter> {
        void showOnlineBookList(List<OnlineReadBean> newsBeans);

        void showMyReadRecordList(List<OnlineReadBean> newsBeans);

        void showMyThinkingList(List<OnlineReadBean> newsBeans);

    }
}
