package com.south.worker.ui.online_read.read_thinking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baselib.utils.ActivityUtils;
import com.baselib.utils.KeyBoardUtils;
import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.online_read.OnlineReadPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class AddReadThinkingFragment extends BaseFragment{



    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.edtContent)
    EditText edtContent;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    int mBookId;
    String mBookName;
    @BindView(R.id.tvBookName)
    TextView tvBookName;

    public static AddReadThinkingFragment newInstance() {

        Bundle args = new Bundle();
        AddReadThinkingFragment fragment = new AddReadThinkingFragment();
        fragment.setArguments(args);
        return fragment;
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_thinking, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        tvMidTitle.setText("添加读书心得");

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llLeft, R.id.btnSubmit,R.id.lladdBook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeft:
                getActivity().onBackPressed();
                break;
            case R.id.btnSubmit:
                KeyBoardUtils.closeKeybord(edtContent,getContext());
                addReadThinking();
                break;
            case R.id.lladdBook:
                KeyBoardUtils.closeKeybord(edtContent,getContext());
                AllBookFragment fragment = AllBookFragment.newInstance();
                OnlineReadPresenter presenter = new OnlineReadPresenter(getContext(),fragment);
                fragment.setPresenter(presenter);
                fragment.setOnDestoryListener(new AllBookFragment.OnDestoryListener() {
                    @Override
                    public void onDestory(int bookId, String bookName) {
                        mBookId = bookId;
                        mBookName = bookName;
                        tvBookName.setText(bookName);
                    }
                });
                ActivityUtils.addFragmentToFragment(getFragmentManager(),this,fragment,R.id.fragment_container);

                break;
        }
    }


    private void addReadThinking() {

        ReadThinkingBean bean = new ReadThinkingBean();
        bean.BookId = mBookId;
        bean.BookName = mBookName;
        bean.UserId = getUserId();
        bean.UserName = getUserName();
        bean.Content = edtContent.getText().toString();

        if( bean.BookId <= 0 || TextUtils.isEmpty(bean.BookName) ){
            showTipDialog("请选择图书");
            return;
        }

        if(TextUtils.isEmpty(bean.Content)){
            showTipDialog("请填写读书心得");
            return;
        }


        BookReposity.getInstance()
                .addReadThinking(bean)
                .subscribe(new LoadingSubscriber<RespondBean>(getContext(), getActivity().getString(R.string.msg_loading), true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                        showTipDialog(respondBean.Msg);
                        if (respondBean.IsOk) {
                            getActivity().onBackPressed();
                        }

                    }

                    @Override
                    public void onSubscriberError(String errorMsg) {
                        showTipDialog(errorMsg);
                    }

                });

    }


}
