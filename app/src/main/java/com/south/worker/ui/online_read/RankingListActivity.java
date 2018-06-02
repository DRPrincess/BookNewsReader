package com.south.worker.ui.online_read;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.ui.BaseActivity;

public class RankingListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        RankingListFragment fragment = RankingListFragment.newInstance();
        RankingListPresenter presenter = new RankingListPresenter(this, fragment);
        fragment.setPresenter(presenter);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);

    }

}
