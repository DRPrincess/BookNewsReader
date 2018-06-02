package com.south.worker.ui.my_part;

import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.BasePresenter;
import com.south.worker.ui.BaseView;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartContact {


    static interface Presenter extends BasePresenter {

        void getData(int page, int pageNum, String type, String searchContent);

    }
    static interface View extends BaseView<Presenter> {

        void showData(List<PartActivityBean> newsBeans);
    }
}
