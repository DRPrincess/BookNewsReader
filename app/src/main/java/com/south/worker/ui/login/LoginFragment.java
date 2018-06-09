package com.south.worker.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.baselib.utils.KeyBoardUtils;
import com.baselib.utils.SharedPreferencesUtil;
import com.baseres.ClearableEditText;
import com.south.worker.R;
import com.south.worker.constant.IntentConfig;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：登录页面
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class LoginFragment extends BaseFragment implements LoginContact.View {

    LoginContact.Presenter mPresenter;
    @BindView(R.id.edtUserName)
    ClearableEditText edtUserName;
    @BindView(R.id.edtPassword)
    ClearableEditText edtPassword;
    @BindView(R.id.checkboxRememberPassword)
    CheckBox checkboxRememberPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    Unbinder unbinder;
    boolean isTokenRunOut;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        //显示登录超时
        if (getActivity().getIntent() != null && getActivity().getIntent().getExtras() != null) {
            isTokenRunOut = "tokenRunOut".equals(getActivity().getIntent().getExtras().getString(IntentConfig.INTENT_KEY_LOGIN));
            if (isTokenRunOut) {
                showToast(getString(R.string.login_token_out));
            }
        }

        if (SharedPreferencesUtil.getBoolean(getContext(),SharedPreferencesConfig.SHARED_KEY_REMEMBER_PASSWORD,false)){
            checkboxRememberPassword.setChecked(true);
        }else{
            checkboxRememberPassword.setChecked(false);
        }

        String userName = SharedPreferencesUtil.getString(getContext(),SharedPreferencesConfig.SHARED_KEY_USER_MOBILE,"");
        String password = SharedPreferencesUtil.getString(getContext(),SharedPreferencesConfig.SHARED_KEY_USER_PASSWORD,"");
        edtUserName.setText(userName);
        edtPassword.setText(password);

        return rootView;
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.checkboxRememberPassword, R.id.btnLogin, R.id.tvContactManager})
    public void onViewClicked(View view) {
        KeyBoardUtils.closeKeybord(edtPassword,getContext());
        switch (view.getId()) {
            case R.id.checkboxRememberPassword:
                break;
            case R.id.btnLogin:
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();

                if(TextUtils.isEmpty(userName)){
                    showTipDialog(getString(R.string.login_username_tint));
                }else  if(TextUtils.isEmpty(password)){
                    showTipDialog(getString(R.string.login_password_tint));
                }else{
                    mPresenter.login(userName,password);
                }
                break;
            case R.id.tvContactManager:
                String phone = "15888888888";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean isRememberPassword() {
        return checkboxRememberPassword.isChecked();
    }
}
