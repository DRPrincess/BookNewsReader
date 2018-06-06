package com.south.worker.ui.online_read;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baselib.utils.KeyBoardUtils;
import com.baselib.utils.LogUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.CommonWebActivity;
import com.south.worker.ui.EditActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadFragment extends BaseFragment implements OnlineReadContact.View {

    private static final String TAG = "OnlineReadFragment";
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnSearch)
    TextView btnSearch;
    @BindView(R.id.rbtnAllBooks)
    RadioButton rbtnAllBooks;
    @BindView(R.id.rbtnMyBooks)
    RadioButton rbtnMyBooks;
    @BindView(R.id.rbtnMyThinkings)
    RadioButton rbtnMyThinkings;
    @BindView(R.id.rgOnlineRead)
    RadioGroup rgOnlineRead;
    @BindView(R.id.recyclerViewContents)
    LRecyclerView recyclerViewContents;
    @BindView(R.id.layoutRankingList)
    RelativeLayout layoutRankingList;
    Unbinder unbinder;
    OnlineReadContact.Presenter mPresenter;

    int page;
    int pageNum = 10;
    String type;

    LRecyclerViewAdapter mAdapter;
    LRecyclerViewAdapter mMyBookAdapter;
    List<OnlineReadBean> mDatas = new ArrayList<>();
    List<OnlineReadBean> mMyOnlineDatas = new ArrayList<>();


    public static OnlineReadFragment newInstance() {

        Bundle args = new Bundle();

        OnlineReadFragment fragment = new OnlineReadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_online_read, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.grey_light));

        initView();

        return rootView;
    }

    /**
     * 初始化控件
     */
    private void initView() {


        initTopListView();

        rgOnlineRead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                LogUtils.d("rgOnlineRead" + checkedId);

                switch (checkedId) {
                    case R.id.rbtnAllBooks:
                        layoutRankingList.setVisibility(View.VISIBLE);
                        type = "0";
                        break;
                    case R.id.rbtnMyBooks:
                        layoutRankingList.setVisibility(View.VISIBLE);
                        type = "1";
                        break;
                    case R.id.rbtnMyThinkings:
                        layoutRankingList.setVisibility(View.GONE);
                        type = "2";

                        break;
                }
                switchTopListView();
                initData();

            }
        });

        rbtnAllBooks.setChecked(true);

    }


    /**
     * 初始化数据
     */
    private void initData() {
        page = 1;
        KeyBoardUtils.closeKeybord(edtSearch, getContext());
        switch (type) {
            case "0":
                mPresenter.getOnlineBook(page, pageNum, edtSearch.getText().toString());
                break;
            case "1":
                mPresenter.getMyReadRecord(page, pageNum, edtSearch.getText().toString());
                break;
            case "2":
                mPresenter.getMyThinking(page, pageNum, edtSearch.getText().toString());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private void switchTopListView(){
        switch (type) {
            case "0":
                recyclerViewContents.setLayoutManager(new GridLayoutManager(getContext(), 4));
                recyclerViewContents.setAdapter(mAdapter);
                recyclerViewContents.setPullRefreshEnabled(false);
                recyclerViewContents.setLoadMoreEnabled(false);
                break;
            case "1":
                recyclerViewContents.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerViewContents.setAdapter(mAdapter);
                recyclerViewContents.setPullRefreshEnabled(false);
                recyclerViewContents.setLoadMoreEnabled(false);
                break;

            case "2":
                recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerViewContents.setAdapter(mAdapter);
                recyclerViewContents.setPullRefreshEnabled(false);
                recyclerViewContents.setLoadMoreEnabled(false);
                break;
        }




    }

    /**
     * 初始化顶部列表
     */
    private void initTopListView() {
        OnlineReadAdapter myPartAdapter = new OnlineReadAdapter(getContext(), mDatas);
        mAdapter = new LRecyclerViewAdapter(myPartAdapter);
        recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewContents.setAdapter(mAdapter);
        recyclerViewContents.setPullRefreshEnabled(false);
        recyclerViewContents.setLoadMoreEnabled(false);
        //设置底部加载文字提示
        recyclerViewContents.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        //设置底部加载颜色
        recyclerViewContents.setFooterViewColor(R.color.grey, R.color.grey, R.color.white);


        recyclerViewContents.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();

            }
        });
        recyclerViewContents.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                switch (type) {
                    case "0":
                        mPresenter.getOnlineBook(page, pageNum, edtSearch.getText().toString());
                        break;
                    case "1":
                        mPresenter.getMyReadRecord(page, pageNum, edtSearch.getText().toString());
                        break;
                    case "2":
                        mPresenter.getMyThinking(page, pageNum, edtSearch.getText().toString());
                        break;
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                OnlineReadBean bean = mDatas.get(position);

                switch (type) {
                    case "0":
                    case "1":

                        CommonWebActivity.startWebActivity(getContext(), "实现中国梦", "http://cpc.people.com.cn/n1/2016/0420/c64094-28289029.html");
                        break;
//                        CommonWebActivity.startWebActivity(getContext(), "习近平谈治国理政", bean.mOnlineBookBean.url);
//                        break;
                    case "2":
                        EditActivity.startEditThinking(getContext(), bean.mReadThinkingBean.content);
                        break;
                }
            }
        });
    }



    @Override
    public void setPresenter(OnlineReadContact.Presenter presenter) {
        mPresenter = presenter;
    }


    private void solveTopList(List<OnlineReadBean> datas) {
        recyclerViewContents.refreshComplete(pageNum);

        if ((mDatas == null || mDatas.size() <= 0) && page > 1) {
            recyclerViewContents.setNoMore(true);
        } else {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            if (page == 1) {
                mDatas.clear();
            }
        }
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOnlineBookList(List<OnlineReadBean> datas) {
        solveTopList(datas);
    }



    @Override
    public void showMyReadRecordList(List<OnlineReadBean> datas) {
        solveTopList(datas);
    }

    @Override
    public void showMyThinkingList(List<OnlineReadBean> datas) {
        solveTopList(datas);
    }

    @OnClick({R.id.btnSearch, R.id.btnRankingList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                recyclerViewContents.forceToRefresh();
                break;
            case R.id.btnRankingList:
                Intent intent = new Intent();
                intent.setClass(getContext(), RankingListActivity.class);
                startActivity(intent);
                break;
        }
    }

}
