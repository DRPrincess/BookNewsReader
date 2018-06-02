package com.south.worker.ui.user_info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;

public class EditUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), EditUserInfoFragment.newInstance(), R.id.fragment_container);

    }
}
