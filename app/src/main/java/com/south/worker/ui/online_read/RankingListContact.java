package com.south.worker.ui.online_read;

import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class RankingListContact {


    static interface Presenter extends BasePresenter {

        void getData(int type,int period);
        void getMyData(int userId,int period);
        void getMyPartData(int partId,int period);


    }
    static interface View extends BaseView<Presenter> {

        void showData(List<ReadRankingBean> readRankingBeans);

        void showRank(String score,int num);
    }
}
