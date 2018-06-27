package com.south.worker.ui.my_part;

import android.content.Context;
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
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.CommonWebActivity;
import com.south.worker.ui.user_info.GlideApp;
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

public class MyPartFragment extends BaseFragment implements MyPartContact.View {

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnSearch)
    TextView btnSearch;
    @BindView(R.id.rbtnNews)
    RadioButton rbtnNews;
    @BindView(R.id.rbtnMonthlyNews)
    RadioButton rbtnMonthlyNews;
    @BindView(R.id.rbtnPartStar)
    RadioButton rbtnPartStar;
    @BindView(R.id.rgMyPart)
    RadioGroup rgMyPart;
    @BindView(R.id.recyclerViewContents)
    LRecyclerView recyclerViewContents;
    @BindView(R.id.emptyView)
    View emptyView;
    Unbinder unbinder;

    Banner banner;

    MyPartContact.Presenter mPresenter;
    LRecyclerViewAdapter mAdapter;
    List<PartActivityBean> mDatas = new ArrayList<>();


    ArrayList<String> imgUrls;
    ArrayList<BannerBean> mBannerBeans;


    int page;
    int pageNum = 10;
    int type;

    public static MyPartFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyPartFragment fragment = new MyPartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_part, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.grey_light));

        initView();


        return rootView;
    }

    @Override
    public void onResume() {
        recyclerViewContents.forceToRefresh();
        super.onResume();
    }

    private void initView() {


        rgMyPart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.rbtnNews:
                        type = 0;
                        break;
                    case R.id.rbtnMonthlyNews:
                        type = 1;
                        break;
                    case R.id.rbtnPartStar:
                        type = 2;
                        break;

                }

                if(mDatas != null){
                    mDatas.clear();
                    if(mAdapter != null){
                        mAdapter.notifyDataSetChanged();
                    }
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


    private void initData(){
        page = 1;
        mPresenter.getBanner(getPartId());
        mPresenter.getData(page,pageNum,getPartId(),type,edtSearch.getText().toString());
    }


    /**
     * 初始化轮播图
     */
    private  void initBanner(){

        imgUrls = new ArrayList<>();
        mBannerBeans = new ArrayList<>();
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideApp
                        .with(context)
                        .load(path)
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .into(imageView);
            }
        });


        //设置图片集合
        banner.setImages(imgUrls);

        //设置轮播图片间隔时间（单位毫秒，默认为2000）
        banner.setDelayTime(3000);

        //点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                if(mBannerBeans == null || mBannerBeans.size() <=position){
                    return;
                }

                BannerBean bannerBean = mBannerBeans.get(position);

                CommonWebActivity.startWebActivity(getActivity(),bannerBean.NewsId);
//                if(bannerBean != null){
//                    mPresenter.getNewsUrl(bannerBean.NewsId);
//                }

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

        MyPartAdapter myPartAdapter = new MyPartAdapter(getContext(),mDatas );
        mAdapter = new LRecyclerViewAdapter(myPartAdapter);
        recyclerViewContents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewContents.setAdapter(mAdapter);
        recyclerViewContents.setPullRefreshEnabled(true);
        recyclerViewContents.setLoadMoreEnabled(true);

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
                mPresenter.getData(page, pageNum, getPartId(), type,edtSearch.getText().toString());
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                PartActivityBean bean = mDatas.get(position);
                if(bean == null){
                    return;
                }

//                mPresenter.getNewsUrl(bean.Id);

                CommonWebActivity.startWebActivity(getActivity(),bean.Id);
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
    public void setPresenter(MyPartContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showData(List<PartActivityBean> newsBeans) {

        recyclerViewContents.refreshComplete(pageNum);

        if((newsBeans == null || newsBeans.size() <= 0) && page>1 ){
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
    public void showBanner(List<String> imgUrls, List<BannerBean> bannerBeans) {
        this.imgUrls.clear();
        this.mBannerBeans.clear();

        this.imgUrls.addAll(imgUrls);
        this.mBannerBeans.addAll(bannerBeans);

        if(imgUrls != null && !imgUrls.isEmpty()){
            banner.update(imgUrls);
        }


    }

    @Override
    public void noData() {
        showData(new ArrayList<PartActivityBean>());
    }
}
