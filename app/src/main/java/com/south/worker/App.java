package com.south.worker;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.igexin.sdk.PushManager;
import com.south.worker.push.MyGTIntentService;
import com.south.worker.push.PushService;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述   ：应用实例类
 * <p>
 * 作者   ：Created by DR on 2018/5/31.
 */

public class App extends Application{

    private static App INSTANCE;

    public static App  getInstance(){
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyGTIntentService.class);



    }






    List<Activity> mActivities = new ArrayList<>();
    public void add(Activity activity) {
        if (!mActivities.contains(activity))
            mActivities.add(activity);
    }

    public final List<Activity> getActivityList() {
        return mActivities;
    }

    public void remove(Activity activity) {
        if (mActivities.contains(activity))
            mActivities.remove(activity);
    }

    public void finishProgram() {
        for (Activity activity : mActivities) {
            if (activity != null)
                activity.finish();
        }
        System.exit(0);
    }



}
