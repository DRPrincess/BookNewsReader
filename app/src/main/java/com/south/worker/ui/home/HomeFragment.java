package com.south.worker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baselib.utils.KeyBoardUtils;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.CommonWebActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

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

public class HomeFragment extends BaseFragment implements HomeContact.View {

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rbtnNews)
    RadioButton rbtnNews;
    @BindView(R.id.rbtnNotices)
    RadioButton rbtnNotices;
    @BindView(R.id.rbtnPartActivities)
    RadioButton rbtnPartActivities;
    @BindView(R.id.rbtnOnlinePartClasses)
    RadioButton rbtnOnlinePartClasses;
    @BindView(R.id.rgHome)
    RadioGroup rgHome;

    @BindView(R.id.recyclerViewContents)
    LRecyclerView recyclerViewContents;
    @BindView(R.id.emptyView)
    View emptyView;
    Unbinder unbinder;

    Banner banner;



    HomeContact.Presenter mPresenter;
    LRecyclerViewAdapter mAdapter;
    List<NewsBean> mDatas = new ArrayList<>();


    ArrayList<String> imgUrls;
    ArrayList<String> titles;
    ArrayList<String> linkUrls;


    int page;
    int pageNum = 10;
    int type;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.grey_light));


        initView();


        mPresenter.getNewsUrl(1);

        return rootView;
    }

    @Override
    public void onResume() {
        recyclerViewContents.forceToRefresh();
        super.onResume();
    }

    private void initData(){
        page = 1;
        mPresenter.getBanner();
        mPresenter.getData(page,pageNum,type,edtSearch.getText().toString());
    }

    private void initView() {

        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.rbtnNews:
                        type = 0;
                        break;
                    case R.id.rbtnNotices:
                        type = 1;
                        break;
                    case R.id.rbtnPartActivities:
                        type = 2;
                        break;
                    case R.id.rbtnOnlinePartClasses:
                        type = 3;
                        break;

                }

                recyclerViewContents.scrollToPosition(0);
                recyclerViewContents.forceToRefresh();
            }
        });


        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH://按下搜索键
                        recyclerViewContents.forceToRefresh();
                        break;
                    default:
                        break;
                }
                return false;

            }
        });




        initList();

        initBanner();

        rbtnNews.setChecked(true);

    }


    /**
     * 初始化轮播图
     */
    private  void initBanner(){

        imgUrls = new ArrayList<>();
        titles = new ArrayList<>();
        linkUrls = new ArrayList<>();
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(path).into(imageView);

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }
        });

        //本地图片数据（资源文件）
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.show_data_news_weixin15);
        images.add(R.drawable.show_data_news_weixin16);
        images.add(R.drawable.show_data_news_weixin17);

        titles.add("驾驶史诗级的机车 写着新时代的传奇");
        titles.add("为梦想不负芳华");
        titles.add("这句话咱铁路人都知道，竟然出自这里");

        linkUrls.add("https://mp.weixin.qq.com/s/OFZd9zRnQIG_wpFa7awHoA");
        linkUrls.add("https://mp.weixin.qq.com/s/nhphJjy-LA9X3564jcKGew");
        linkUrls.add("https://mp.weixin.qq.com/s/3QDBT7UPeVFxXRPjt-PNzA");

        //设置图片集合
        banner.setImages(images);

        //设置轮播图片间隔时间（单位毫秒，默认为2000）
        banner.setDelayTime(3000);

        //点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                CommonWebActivity.startWebActivity(getContext(),titles.get(position),linkUrls.get(position));

            }
        });


        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    /**
     * 初始化列表
     */
    private void initList(){

        View headView = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_header,null);
        banner = headView.findViewById(R.id.banner);

        HomeAdapter homeAdapter = new HomeAdapter(getContext(),mDatas );
        mAdapter = new LRecyclerViewAdapter(homeAdapter);
        recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewContents.setAdapter(mAdapter);
        recyclerViewContents.setPullRefreshEnabled(true);
        recyclerViewContents.setLoadMoreEnabled(false);

        mAdapter.addHeaderView(headView);


        //设置底部加载文字提示
        recyclerViewContents.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        //设置底部加载颜色
        recyclerViewContents.setFooterViewColor(R.color.grey, R.color.grey, R.color.white);

        recyclerViewContents.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                KeyBoardUtils.closeKeybord(edtSearch,getContext());
                initData();

            }
        });
        recyclerViewContents.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getData(page, pageNum, type,edtSearch.getText().toString());
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                mPresenter.getNewsUrl(1);
            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        recyclerViewContents.forceToRefresh();
    }

    @Override
    public void setPresenter(HomeContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showData(List<NewsBean> newsBeans) {

        recyclerViewContents.refreshComplete(pageNum);

        if((mDatas == null || mDatas.size() <= 0) && page>1 ){
            recyclerViewContents.setNoMore(true);
        }else{
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            if(page == 1){
                mDatas.clear();
            }
        }
        mDatas.addAll(newsBeans);
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
    public void startWebActivity(String title, String url) {
        CommonWebActivity.startWebActivity(getContext(),title,url);
    }

    @Override
    public void showBanner(List<String> imgUrls, List<String> titles, List<String> linkUrls) {
        this.imgUrls.clear();
        this.titles.clear();
        this.linkUrls.clear();

        this.imgUrls.addAll(imgUrls);
        this.titles.addAll(titles);
        this.linkUrls.addAll(linkUrls);

        banner.update(imgUrls);
    }

}
