package com.south.worker.ui.online_read.read_thinking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：读书心得详情
 * <p>
 * 作者   ：Created by DR on 2018/6/13.
 */

public class ReadThinkingDetailFragment extends BaseFragment {

    private  final static String ARG_THINKING_ID = "thinking_id";
    private  final static String ARG_BOOKNAME = "book_name";
    private  final  static String ARG_CONTENT = "content";
    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.tvBookName)
    TextView tvBookName;
    @BindView(R.id.tvConent)
    TextView tvConent;
    Unbinder unbinder;
    int thinkingId ;
    String content ;
    String bookName;


    public static ReadThinkingDetailFragment newInstance(int thinkingId,String bookName, String content) {

        Bundle args = new Bundle();
        args.putInt(ARG_THINKING_ID,thinkingId);
        args.putString(ARG_BOOKNAME,bookName);
        args.putString(ARG_CONTENT,content);
        ReadThinkingDetailFragment fragment = new ReadThinkingDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            thinkingId = getArguments().getInt(ARG_THINKING_ID);
            bookName = getArguments().getString(ARG_BOOKNAME);
            content = getArguments().getString(ARG_CONTENT);
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thinking_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        tvMidTitle.setText("读书心得详情");
        tvBookName.setText(String.format("《%s》",bookName));
        tvConent.setText(content);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llLeft,R.id.ivAddLike})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.llLeft:
                getActivity().onBackPressed();
                break;
            case R.id.ivAddLike:
                likeReadThinking(getUserId(),thinkingId,1);
                break;
        }

    }

    public void likeReadThinking(int userId, int thinkingid, int num) {
        BookReposity.getInstance()
                .likeReadThinking(userId,thinkingid,num)
                .subscribe(new LoadingSubscriber<RespondBean>(getContext(),getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                       showTipDialog(respondBean.Msg);


                    }
                    @Override
                    public void onSubscriberError(String errorMsg) {
                       showTipDialog(errorMsg);
                    }

                });
    }
}
