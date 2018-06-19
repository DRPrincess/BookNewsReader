package com.south.worker.ui.online_read;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baselib.utils.ActivityUtils;
import com.baselib.utils.KeyBoardUtils;
import com.baselib.utils.LogUtils;
import com.baselib.utils.SharedPreferencesUtil;
import com.baselib.utils.StringUtils;
import com.baselib.utils.TimeUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.ReadBookTimeBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.CommonWebActivity;
import com.south.worker.ui.online_read.read_thinking.AddReadThinkingActivity;
import com.south.worker.ui.online_read.read_thinking.AddReadThinkingFragment;
import com.south.worker.ui.online_read.read_thinking.ReadThinkingDetailActivity;
import com.south.worker.ui.online_read.read_thinking.ReadThinkingDetailFragment;

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
    private static final int REQUEST_CODE_READ = 0x01;
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
    @BindView(R.id.layoutAddThinking)
    RelativeLayout layoutAddThinking;
    @BindView(R.id.emptyView)
    View emptyView;


    Unbinder unbinder;
    OnlineReadContact.Presenter mPresenter;

    int page;
    int pageNum = 10;
    String type;

    LRecyclerViewAdapter mAdapter;
    List<OnlineReadBean> mDatas = new ArrayList<>();
    ReadBookTimeBean bookTimeBean = new ReadBookTimeBean();

    public static OnlineReadFragment newInstance() {

        Bundle args = new Bundle();

        OnlineReadFragment fragment = new OnlineReadFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerViewContents.forceToRefresh();
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
                        layoutAddThinking.setVisibility(View.GONE);
                        type = "0";
                        break;
                    case R.id.rbtnMyBooks:
                        layoutRankingList.setVisibility(View.VISIBLE);
                        layoutAddThinking.setVisibility(View.GONE);
                        type = "1";
                        break;
                    case R.id.rbtnMyThinkings:
                        layoutRankingList.setVisibility(View.GONE);
                        layoutAddThinking.setVisibility(View.VISIBLE);
                        type = "2";

                        break;
                }
                switchTopListView();
                recyclerViewContents.forceToRefresh();

            }
        });

        rbtnAllBooks.setChecked(true);

    }


    /**
     * 初始化数据
     */
    private void initData() {
        KeyBoardUtils.closeKeybord(edtSearch, getContext());
        page = 1;
        switch (type) {
            case "0":
                mPresenter.getOnlineBook(page, pageNum, edtSearch.getText().toString());
                break;
            case "1":
                mPresenter.getMyReadRecord(getUserId(),page, pageNum, edtSearch.getText().toString());
                break;
            case "2":
                mPresenter.getMyThinking(getUserId(),page, pageNum, edtSearch.getText().toString());
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
                recyclerViewContents.setPullRefreshEnabled(true);
                recyclerViewContents.setLoadMoreEnabled(true);
                break;
            case "1":
                recyclerViewContents.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerViewContents.setAdapter(mAdapter);
                recyclerViewContents.setPullRefreshEnabled(true);
                recyclerViewContents.setLoadMoreEnabled(true);
                break;

            case "2":
                recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerViewContents.setAdapter(mAdapter);
                recyclerViewContents.setPullRefreshEnabled(true);
                recyclerViewContents.setLoadMoreEnabled(true);
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
                        mPresenter.getMyReadRecord(getUserId(),page, pageNum, edtSearch.getText().toString());
                        break;
                    case "2":
                        mPresenter.getMyThinking(getUserId(),page, pageNum, edtSearch.getText().toString());
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

                        if(TextUtils.isEmpty(bean.mOnlineBookBean.BookName) || StringUtils.isHttpUrl(bean.mOnlineBookBean.BookUrl)){
                            showTipDialog("该图书链接无效");
                            return;
                        }else {
                            mPresenter.addMyBook(getUserId(),bean.mOnlineBookBean.Id,bean.mOnlineBookBean.BookName,bean.mOnlineBookBean.BookUrl);
                        }
                        break;

                    case "1":
                        if(TextUtils.isEmpty(bean.mMyBookBean.Url) || StringUtils.isHttpUrl(bean.mMyBookBean.Url)){
                            showTipDialog("该图书链接无效");
                            return;
                        }else{

                            startWebActivity(bean.mMyBookBean.BookName,bean.mMyBookBean.Url,bean.mMyBookBean.BookId,1);
                        }

                        break;
                    case "2":
                        ReadThinkingBean thinkingBean = bean.mReadThinkingBean;
                        ReadThinkingDetailActivity.startReadThinkingDetailActivity(getContext(),thinkingBean.Id,thinkingBean.BookName,thinkingBean.Content);

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

        if ((datas == null || datas.isEmpty())&& page >1 ){
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


        if(mDatas.size()==0){
            recyclerViewContents.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            recyclerViewContents.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
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

    @OnClick({R.id.btnSearch, R.id.btnRankingList,R.id.btnAddThinking})
    public void onViewClicked(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnSearch:
                recyclerViewContents.forceToRefresh();
                break;
            case R.id.btnRankingList:
                intent.setClass(getContext(), RankingListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAddThinking:
                intent.setClass(getContext(), AddReadThinkingActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void startWebActivity(String title, String url,int bookId,int type) {

        bookTimeBean.BookId = bookId;
        bookTimeBean.Type = type;

        SharedPreferencesUtil.saveData(getContext(),SharedPreferencesConfig.SHARED_KEY_USER_READ_BOOK_START_TIME,TimeUtils.getCurrentDate());
        Intent intent = new Intent();
        intent.putExtra(CommonWebActivity.URL, url);
        intent.putExtra(CommonWebActivity.TITLE, title);
        intent.setClass(getContext(), CommonWebActivity.class);
        startActivityForResult(intent,REQUEST_CODE_READ);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_READ){

            String currentTime = TimeUtils.getCurrentDate();
            String startTime = SharedPreferencesUtil.getString(getContext(), SharedPreferencesConfig.SHARED_KEY_USER_READ_BOOK_START_TIME,currentTime);
            long readingTimeMinute = TimeUtils.getMinuteBetweenTwoDate(startTime,currentTime);


            bookTimeBean.UserId = getUserId();
            bookTimeBean.BranchId = getPartId();
            bookTimeBean.LengthofReadingTime = readingTimeMinute;
            bookTimeBean.Time = TimeUtils.getCurrentDate();

            mPresenter.addReadBook(bookTimeBean);

        }

    }



}
