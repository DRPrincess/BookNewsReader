package com.south.worker.ui.online_read.read_thinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.ui.BaseActivity;
import com.south.worker.ui.online_read.OnlineReadFragment;

public class AddReadThinkingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        AddReadThinkingFragment fragment = AddReadThinkingFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment ,R.id.fragment_container);


    }
}
