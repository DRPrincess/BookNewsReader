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
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadPresenter implements OnlineReadContact.Presenter {

    Context mContext;
    OnlineReadContact.View mView;

    int[] imageIds = {R.drawable.show_data_book1,
            R.drawable.show_data_book2,
            R.drawable.show_data_book3,
            R.drawable.show_data_book4,
            R.drawable.show_data_book5,
            R.drawable.show_data_book6,
            R.drawable.show_data_book7,
            R.drawable.show_data_book8};


    String[] urls = {
            "http://www.12371.cn/special/blqs/39zyzgm/",
            "http://www.12371.cn/special/blqs/xjpsd/",
            "http://www.12371.cn/special/blqs/dssj/30pyjr/",
            "http://www.12371.cn/special/blqs/xjpfxzg/",
            "http://www.12371.cn/special/blqs/xjpzsjxlzyjhdb2016/",
            "http://www.12371.cn/special/blqs/xjpfxzg/",
            "http://www.12371.cn/special/blqs/xjptzglz/",
            "http://www.12371.cn/special/blqs/xjpsd/"};

    String[] names = {"马格斯读后感",
            "党员十项读后感",
            "正能量读后感",
            "廉政十讲读后感"};

    String[] thinks = {"习近平作为中国党和国家的最高领导人，围绕治国理政发表了大量讲话，提出了许多新思想、新观点、新论断，深刻回答了新的历史条件下党和国家发展的重大理论和现实问题",
            "马格斯真是一个伟大的思想家，政治家，一位伟大的人",
            "做一个有正能量的党员，给组织带来正常的影响",
            "富有进取精神，淡泊名利权位，志在事业有成。即使百尺竿头，也要更进一步"};

    public OnlineReadPresenter(Context context, OnlineReadContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getOnlineBook(int page, int pageNum, String searchContent) {

        ArrayList<OnlineReadBean> data = new ArrayList<>();




        for (int i = 0; i < 8; i++) {
            OnlineBookBean bookBean = new OnlineBookBean(imageIds[i],urls[i]);
            OnlineReadBean onlineReadBean = new OnlineReadBean("0",bookBean);
            data.add(onlineReadBean);
        }

        mView.showOnlineBookList(data);

    }

    @Override
    public void getMyOnlineBook(int page, int pageNum, String searchContent) {
        ArrayList<OnlineReadBean> data = new ArrayList<>();


        for (int i = 0; i < 4; i++) {
            OnlineBookBean bookBean = new OnlineBookBean(imageIds[i],urls[i]);
            OnlineReadBean onlineReadBean = new OnlineReadBean("0",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyOnlineBookList(data);
    }

    @Override
    public void getMyReadRecord(int page, int pageNum, String searchContent) {

        ArrayList<OnlineReadBean> data = new ArrayList<>();


        for (int i = 0; i < 4; i++) {
            MyBookBean bookBean = new MyBookBean(imageIds[i],String.valueOf(new Random().nextInt(6)+1),String.valueOf(new Random().nextInt(200)+80),urls[i]);
            OnlineReadBean onlineReadBean = new OnlineReadBean("1",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyReadRecordList(data);
    }

    @Override
    public void getMyThinking(int page, int pageNum, String searchContent) {
        ArrayList<OnlineReadBean> data = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            ReadThinkingBean bookBean = new ReadThinkingBean(names[new Random().nextInt(3)],thinks[new Random().nextInt(3)]);
            OnlineReadBean onlineReadBean = new OnlineReadBean("2",bookBean);
            data.add(onlineReadBean);
        }

        mView.showMyThinkingList(data);
    }
}
