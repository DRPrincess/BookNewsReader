package com.south.worker.ui.online_read.read_thinking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baselib.utils.KeyBoardUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.online_read.OnlineReadContact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/12.
 */

public class AllBookFragment extends BaseFragment implements OnlineReadContact.AllBookView {

    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.btnSearch)
    TextView btnSearch;
    @BindView(R.id.recyclerViewContents)
    LRecyclerView recyclerViewContents;
    Unbinder unbinder;
    OnlineReadContact.Presenter mPresenter;
    LRecyclerViewAdapter mAdapter;
    List<OnlineBookBean> mDatas = new ArrayList<>();
    int page;
    int pageNum = 10;
    String type;
    OnDestoryListener mOnDestoryListener;
    @BindView(R.id.edtSearch)
    EditText edtSearch;


    public static AllBookFragment newInstance() {

        Bundle args = new Bundle();

        AllBookFragment fragment = new AllBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_book, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.grey_light));

        initView();

        recyclerViewContents.forceToRefresh();


        return rootView;
    }


    /**
     * 初始化控件
     */
    private void initView() {


        tvMidTitle.setText("选择图书");

        mDatas = new ArrayList<>();
        AllBookAdapter allBookAdapter = new AllBookAdapter(getContext(), mDatas);
        mAdapter = new LRecyclerViewAdapter(allBookAdapter);
        recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewContents.setAdapter(mAdapter);
        recyclerViewContents.setPullRefreshEnabled(true);
        recyclerViewContents.setLoadMoreEnabled(true);
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
                mPresenter.getOnlineBook(page, pageNum, edtSearch.getText().toString());
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                OnlineBookBean bean = mDatas.get(position);
                if (mOnDestoryListener != null) {
                    mOnDestoryListener.onDestory(bean.Id, bean.BookName);
                    getActivity().onBackPressed();
                }


            }
        });


    }

    /**
     * 初始化数据
     */
    private void initData() {
        page = 1;
        mPresenter.getOnlineBook(page, pageNum, edtSearch.getText().toString());

    }

    @Override
    public void setPresenter(OnlineReadContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showOnlineBookList(List<OnlineBookBean> newsBeans) {
        recyclerViewContents.refreshComplete(pageNum);

        if (page == 1) {
            mDatas.clear();
        }
        mDatas.addAll(newsBeans);
        mAdapter.notifyDataSetChanged();

    }

    public void setOnDestoryListener(OnDestoryListener onDestoryListener) {
        mOnDestoryListener = onDestoryListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSearch, R.id.llLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                KeyBoardUtils.closeKeybord(edtSearch, getContext());
                recyclerViewContents.forceToRefresh();
                break;
            case R.id.llLeft:
                KeyBoardUtils.closeKeybord(edtSearch, getContext());
                getActivity().onBackPressed();
                break;
        }
    }

    public interface OnDestoryListener {

        void onDestory(int bookId, String bookName);

    }
}
