package com.south.worker.ui.user_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
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

public class EditUserInfoFragment extends BaseFragment {


    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.ivHeadImg)
    ImageView ivHeadImg;
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtSex)
    EditText edtSex;
    @BindView(R.id.edtBirth)
    EditText edtBirth;
    @BindView(R.id.edtIntoPart)
    EditText edtIntoPart;
    @BindView(R.id.edtEducation)
    EditText edtEducation;
    @BindView(R.id.edtHoppy)
    EditText edtHoppy;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtWeixin)
    EditText edtWeixin;
    Unbinder unbinder;


    public static EditUserInfoFragment newInstance() {

        Bundle args = new Bundle();
        EditUserInfoFragment fragment = new EditUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        tvMidTitle.setText("修改资料");


        edtUserName.setText("王爱国");
        edtSex.setText("男");
        edtBirth.setText("1970.2.15");
        edtIntoPart.setText("1988.3.6");
        edtEducation.setText("研究生");
        edtHoppy.setText("阅读，篮球");
        edtPhone.setText("15356871263");
        edtWeixin.setText("15356871263");



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

                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();


                break;
        }
    }
}
