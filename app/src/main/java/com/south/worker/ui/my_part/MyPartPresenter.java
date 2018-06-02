package com.south.worker.ui.my_part;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.home.HomeContact;

import java.util.ArrayList;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartPresenter implements MyPartContact.Presenter {

    Context mContext;
    MyPartContact.View mView;


    public MyPartPresenter(Context context, MyPartContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<PartActivityBean> data = new ArrayList<>();

        PartActivityBean bean = null;
        switch (type) {

            case "0":
                bean = new PartActivityBean("组织动态-[基层]紧跟新时代  打造好班子",
                        "贯彻落实《中央巡视工作规划（2018—2022年）》,推进会14日在成都召开，中共中央政治局常...",
                        R.drawable.user_show_data8, "");
                break;
            case "1":
                bean = new PartActivityBean("月度写实-[基层]紧跟新时代  打造好班子",
                        "贯彻落实《中央巡视工作规划（2018—2022年）》,推进会14日在成都召开，中共中央政治局常...",
                        R.drawable.user_show_data9, "");
                break;
            case "2":
                bean = new PartActivityBean("先进典型-[基层]紧跟新时代  打造好班子",
                        "贯彻落实《中央巡视工作规划（2018—2022年）》,推进会14日在成都召开，中共中央政治局常...",
                        R.drawable.user_show_data10, "");
                break;


        }

        for (int i = 0; i < 10; i++) {
            data.add(bean);
        }

        mView.showData(data);
    }
}
