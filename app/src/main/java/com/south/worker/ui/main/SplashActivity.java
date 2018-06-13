package com.south.worker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.baselib.utils.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.constant.IntentConfig;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.ui.BaseActivity;
import com.south.worker.ui.login.LoginActivity;
import com.south.worker.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(1, TimeUnit.SECONDS)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        int userId = SharedPreferencesUtil.getInt(getBaseContext(), SharedPreferencesConfig.SHARED_KEY_USER_ID,-1);
                        if(userId == -1){
                            goLogin();
                        }else{
                            goMain();
                        }
                    }
                })
                .subscribe();


    }



    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void goMain() {

        Intent intent = new Intent(this, MainActivity.class);
        if(getIntent() != null && getIntent().getIntExtra(IntentConfig.INTENT_KEY_PUSH_NEWS_ID,-1) != -1){
            intent.putExtra(IntentConfig.INTENT_KEY_PUSH_NEWS_ID,getIntent().getIntExtra(IntentConfig.INTENT_KEY_PUSH_NEWS_ID,-1));
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
