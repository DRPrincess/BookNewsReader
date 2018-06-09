package com.south.worker.ui.online_read;

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
import android.widget.Toast;

import com.south.worker.R;
import com.south.worker.data.BookReposity;
import com.south.worker.data.bean.ReadRankingBean;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.BaseFragment;

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

public class EditReadThinkingFragment extends BaseFragment {
    private   static String ARG_TEXT = "text";
    private   static String ARG_BOOK_ID = "bookId";
    private   static String ARG_BOOK_NAME = "bookName";

    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.edtContent)
    EditText edtContent;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    String text ;
    int bookId;
    String bookName;

    public static EditReadThinkingFragment newInstance(String text,int bookId,String bookName) {

        Bundle args = new Bundle();
        args.putString(ARG_TEXT,text);
        args.putInt(ARG_BOOK_ID,bookId);
        args.putString(ARG_BOOK_NAME,bookName);
        EditReadThinkingFragment fragment = new EditReadThinkingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            text = getArguments().getString(ARG_TEXT);
            bookId = getArguments().getInt(ARG_BOOK_ID);
            bookName = getArguments().getString(ARG_BOOK_NAME);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_sign, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if(!TextUtils.isEmpty(text)){
            edtContent.setText(text);
            edtContent.setSelection(text.length());
        }

        tvMidTitle.setText("编辑读后感");

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llLeft, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeft:
                getActivity().onBackPressed();
                break;
            case R.id.btnSubmit:

                addReadThinking();
                break;
        }
    }


    private void addReadThinking(){

        ReadThinkingBean bean = new ReadThinkingBean();
        bean.BookId = bookId;
        bean.BookName = bookName;
        bean.UserId = getUserId();
        bean.UserName = getUserName();
        bean.Content = edtContent.getText().toString();


        BookReposity.getInstance()
                .addReadThinking(bean)
                .subscribe(new LoadingSubscriber<RespondBean>(getContext(),getActivity().getString(R.string.msg_loading),true) {

                    @Override
                    public void onNext(RespondBean respondBean) {
                        showTipDialog(respondBean.Msg);
                        if (respondBean.IsOk){
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
