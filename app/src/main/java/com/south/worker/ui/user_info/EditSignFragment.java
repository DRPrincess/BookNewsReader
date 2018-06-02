package com.south.worker.ui.user_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.south.worker.R;
import com.south.worker.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class EditSignFragment extends BaseFragment {
    private   static String ARG_TEXT = "text";
    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.edtContent)
    EditText edtSign;
    @BindView(R.id.btnSubmit)
    Button btnEditSign;
    Unbinder unbinder;

    String text ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            text = getArguments().getString(ARG_TEXT);
        }

    }

    public static EditSignFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString(ARG_TEXT,text);
        EditSignFragment fragment = new EditSignFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_sign, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        edtSign.setText(text);

        tvMidTitle.setText("设置签名");


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
                Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
