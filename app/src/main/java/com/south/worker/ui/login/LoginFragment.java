package com.south.worker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.baselib.utils.KeyBoardUtils;
import com.baseres.ClearableEditText;
import com.south.worker.R;
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
        switch (view.getId()) {
            case R.id.checkboxRememberPassword:
                KeyBoardUtils.openKeybord(edtPassword,getContext());
                break;
            case R.id.btnLogin:

                KeyBoardUtils.openKeybord(edtPassword,getContext());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

                break;
            case R.id.tvContactManager:
                break;
        }
    }
}
