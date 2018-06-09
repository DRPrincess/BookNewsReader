package com.south.worker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.utils.SharedPreferencesUtil;
import com.baseres.ClearableEditText;
import com.south.worker.R;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/6.
 */

public class ResetPasswordFragment extends BaseFragment implements ResetPasswordContact.View {

    ResetPasswordContact.Presenter mPresenter;
    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.edtOldPassword)
    ClearableEditText edtOldPassword;
    @BindView(R.id.edtNewPassword)
    ClearableEditText edtNewPassword;
    @BindView(R.id.edtNewPasswordAgain)
    ClearableEditText edtNewPasswordAgain;
    Unbinder unbinder;


    public static ResetPasswordFragment newInstance() {

        Bundle args = new Bundle();

        ResetPasswordFragment fragment = new ResetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(ResetPasswordContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reset_passord, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        tvMidTitle.setText(getString(R.string.reset_password_title));

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

                String  oldPassword = edtOldPassword.getText().toString();
                String  newPassword = edtNewPassword.getText().toString();
                String  newPasswordAgain = edtNewPasswordAgain.getText().toString();

                if(TextUtils.isEmpty(oldPassword)){
                    showTipDialog("请输入当前密码");
                    return;
                }

                if(TextUtils.isEmpty(newPassword)){
                    showTipDialog("请输入新密码");
                    return;
                }

                if(TextUtils.isEmpty(newPasswordAgain)){
                    showTipDialog("请再次输入新密码");
                    return;
                }

                if(!newPassword.equals(newPasswordAgain)){
                    showTipDialog("两次输入的新密码不一致，请检查");
                    return;
                }

                mPresenter.changePassord(getUserId(),oldPassword,newPassword);

                break;
        }
    }


    @Override
    public void LoginAgain() {
        SharedPreferencesUtil.clearData(getActivity(), SharedPreferencesConfig.SHARED_KEY_USER_ID);
        SharedPreferencesUtil.clearData(getActivity(),SharedPreferencesConfig.SHARED_KEY_USER_PART_ID);
        SharedPreferencesUtil.clearData(getActivity(),SharedPreferencesConfig.SHARED_KEY_USER_HEADER_IMG);
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
