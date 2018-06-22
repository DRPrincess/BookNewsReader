package com.south.worker.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.baselib.utils.ApplicationUtils;
import com.baselib.utils.LogUtils;
import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.south.worker.R;
import com.south.worker.constant.IntentConfig;
import com.south.worker.data.NewsReposity;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.network.LoadingSubscriber;
import com.south.worker.ui.CommonWebActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 *
 */
public class MyGTIntentService extends GTIntentService {



    @Override
    public void onReceiveServicePid(Context context, int i) {
        LogUtils.e(TAG, "onReceiveServicePid -> " + "ServicePid = " + i);

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {

        LogUtils.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveMessageData(final Context context, GTTransmitMessage gtTransmitMessage) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "onReceiveMessageData = " + gtTransmitMessage);
        byte[] payload = gtTransmitMessage.getPayload();

        String message = new String(payload);
        LogUtils.e(TAG, "onReceiveMessageData: " + message);

        if(TextUtils.isEmpty(message)){
            return;
        }

        if(ApplicationUtils.isAppAlive(context,getPackageName())){

            Intent intent = new Intent();
            intent.putExtra(CommonWebActivity.NEWSID,Integer.valueOf(message));
            intent.setClass(context, CommonWebActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            /*
            NewsReposity.getInstance()
                    .getNewsUrl(Integer.valueOf(message))
                    .subscribe(new Observer<NewUrlBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NewUrlBean bean) {

                            Intent intent = new Intent();
                            intent.putExtra(CommonWebActivity.Content, bean.Content);
                            intent.putExtra(CommonWebActivity.TITLE, bean.Title);
                            intent.setClass(context, CommonWebActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MyGTIntentService.this, "获取新闻内容出错", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                    */

        }else{
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage(context.getPackageName());
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            launchIntent.putExtra(IntentConfig.INTENT_KEY_PUSH_NEWS_ID,Integer.valueOf(message));
            startActivity(launchIntent);
        }






    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "onReceiveOnlineState = " + b);

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "onReceiveCommandResult = " + gtCmdMessage);
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "onNotificationMessageArrived = " + gtNotificationMessage);




    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "onNotificationMessageClicked = " + gtNotificationMessage);
        LogUtils.e(TAG, "onReceiveClientId -> " + "onNotificationMessageArrived = " + gtNotificationMessage.getContent());

//        GTMessageBean bean = new Gson().fromJson(gtNotificationMessage.getContent(),GTMessageBean.class);



    }


    /**
     * 显示通知
     * @param context
     * @param messageBean
     * @param intents
     */
    private static void notification(Context context, GTMessageBean messageBean, Intent[] intents){
        LogUtils.e(TAG,"notification-->");
        //http://blog.csdn.net/wangbole/article/details/7465385 [解决PendingIntent传递参数为空的问题]
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //用于打开一组activity，这样能避免闪屏的现象，同时推出目标activity时不会直接推出应用
        PendingIntent contentIntent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBean.Title))
                .setTicker(messageBean.Text)
                .setContentTitle(messageBean.Title)
                .setContentText(messageBean.Title)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.push_small)//状态栏里面的图标（小图标）
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.push));//下拉下拉列表里面的图标（大图标）

        if ("1".equals(messageBean.IsRing) && "1".equals(messageBean.IsVibrate)) {
            builder.setDefaults(Notification.DEFAULT_ALL);
        } else if ("1".equals(messageBean.IsVibrate)) {
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else if ("1".equals(messageBean.IsRing)) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
        }
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify((int) System.currentTimeMillis() / 1000, notification);
    }
}
