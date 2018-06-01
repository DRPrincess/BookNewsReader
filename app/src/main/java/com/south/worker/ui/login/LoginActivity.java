package com.south.worker.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baselib.utils.ActivityUtils;
import com.south.worker.App;
import com.south.worker.R;


/**
 *
 *描述   ：登录页面
 *
 *作者   ：Created by DuanRui on 2018/6/1.
 */

public class LoginActivity extends AppCompatActivity {

    long mCurrentTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        LoginFragment fragment = LoginFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        LoginPresenter presenter = new LoginPresenter( this,fragment);
        fragment.setPresenter(presenter);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mCurrentTime) < 2000) {
            App.getInstance().finishProgram();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_exit_app_tips), Toast.LENGTH_SHORT).show();
            mCurrentTime = System.currentTimeMillis();
        }
    }
}
