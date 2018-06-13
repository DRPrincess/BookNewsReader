package com.south.worker.ui.online_read;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baseres.ScrollListView;
import com.south.worker.R;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.ui.BaseFragment;

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

public class RankingListFragment extends BaseFragment  implements RankingListContact.View{

    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.rbtnPeople)
    RadioButton rbtnPeople;
    @BindView(R.id.rbtnPart)
    RadioButton rbtnPart;
    @BindView(R.id.rgRankType)
    RadioGroup rgRankType;
    @BindView(R.id.rgRankPeriod)
    RadioGroup rgRankPeriod;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.lvRank)
    ScrollListView lvRank;
    @BindView(R.id.rbtnWeekly)
    RadioButton rbtnWeekly;
    @BindView(R.id.rbtnMonthly)
    RadioButton rbtnMonthly;
    @BindView(R.id.rbtnQuarterly)
    RadioButton rbtnQuarterly;
    Unbinder unbinder;

    RankingListContact.Presenter mPresenter;

    RankingListAdapter mAdapter;
    List<ReadRankingBean> mDatas;


    int type;
    int period;

    public static RankingListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RankingListFragment fragment = new RankingListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }


    private void initData(){

        if(type == 0){
            mPresenter.getMyData(getUserId(),period);

        }else if(type == 1){
            mPresenter.getMyPartData(getPartId(),period);

        }
        mPresenter.getData(type,period);


    }

    private void initView() {
        tvMidTitle.setText(R.string.ranking_list_title);
        tvScore.setText("暂无数据");
        tvTime.setText("暂无数据");




        rgRankType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtnPeople:
                        type = 0;
                        break;
                    case R.id.rbtnPart:
                        type = 1;
                        break;
                }

                initData();
            }
        });


        rgRankPeriod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtnWeekly:
                        period=0;
                        break;
                    case R.id.rbtnMonthly:
                        period=1;
                        break;
                    case R.id.rbtnQuarterly:
                        period=2;
                        break;
                }
                initData();
            }
        });


        rbtnWeekly.setChecked(true);
        rbtnPeople.setChecked(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.llLeft)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void setPresenter(RankingListContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showData(List<ReadRankingBean> readRankingBeans) {

        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(readRankingBeans);

        if(mAdapter == null){
            mAdapter = new RankingListAdapter(getContext(),mDatas);
            lvRank.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showRank(String score, int num) {
        SpannableString s1 = new SpannableString(score+"分钟");

        s1.setSpan(new AbsoluteSizeSpan(19, true), 0, s1.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvScore.setText(String.format("第%s名",String.valueOf(num)));
        tvTime.setText(s1);
    }
}
