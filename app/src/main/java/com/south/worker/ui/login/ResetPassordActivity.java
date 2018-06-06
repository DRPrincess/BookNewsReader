package com.south.worker.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.ui.BaseActivity;

public class ResetPassordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ResetPasswordFragment fragment = ResetPasswordFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        ResetPasseordPresenter presenter = new ResetPasseordPresenter( this,fragment);
        fragment.setPresenter(presenter);
    }
}
