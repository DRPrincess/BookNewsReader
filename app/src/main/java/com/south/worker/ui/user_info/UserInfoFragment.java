package com.south.worker.ui.user_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.EditActivity;
import com.south.worker.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class UserInfoFragment extends BaseFragment implements UserInfoContact.View {

    @BindView(R.id.ivHeadImg)
    ImageView ivHeadImg;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvSign)
    TextView tvSign;
    Unbinder unbinder;

    UserInfoContact.Presenter mPresenter;


    public static UserInfoFragment newInstance() {

        Bundle args = new Bundle();
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);
//        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(),ivHeadImg);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.red));


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llSign, R.id.llEditInfo, R.id.llCheckUpdate, R.id.llSuggestion, R.id.llLogout})
    public void onViewClicked(View view) {

        Intent intent ;

        switch (view.getId()) {
            case R.id.llSign:
                EditActivity.startEditSign(getContext(),"");
                break;
            case R.id.llEditInfo:
                intent = new Intent(getActivity(),EditUserInfoActivity.class);
                startActivity(intent);

                break;
            case R.id.llCheckUpdate:
                Toast.makeText(getContext(), "已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llSuggestion:
                EditActivity.startEditSuggestion(getContext());
                break;
            case R.id.llLogout:
                intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setPresenter(UserInfoContact.Presenter presenter) {
        mPresenter = presenter;
    }
}
