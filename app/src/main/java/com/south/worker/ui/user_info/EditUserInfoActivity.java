package com.south.worker.ui.user_info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.ui.login.LoginFragment;
import com.south.worker.ui.login.LoginPresenter;

public class EditUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        EditUserInfoFragment fragment =EditUserInfoFragment.newInstance() ;
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        EditUserInfoPresenter presenter = new EditUserInfoPresenter( this,fragment);
        fragment.setPresenter(presenter);


    }
}
