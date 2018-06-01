package com.south.worker.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.south.worker.R;
import com.south.worker.ui.BaseFragment;

/**
 * 描述   ：登录页面
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public class LoginFragment extends BaseFragment implements LoginContact.View{

    LoginContact.Presenter mPresenter;


    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login,container,false);
        return rootView;
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {

        mPresenter = presenter;
    }


}
