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
    }
    static interface View extends BaseView<Presenter> {
        void showOnlineBookList(List<OnlineReadBean> newsBeans);

        void showMyReadRecordList(List<OnlineReadBean> newsBeans);

        void showMyThinkingList(List<OnlineReadBean> newsBeans);

    }
}
