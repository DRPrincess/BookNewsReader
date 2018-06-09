package com.south.worker.data.network;

import android.content.Context;

import com.baseres.MyProgressDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class LoadingSubscriber<T> implements Observer<T> {

    private Context context;
    private String message;
    private boolean show;
    private MyProgressDialog dialog;

    public LoadingSubscriber(Context context) {
        this(context, null, true);
    }

    public LoadingSubscriber(Context context, String message, boolean show) {
        this.context = context;
        this.message = message;
        this.show = show;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (show) {
            if (dialog == null) {
                dialog = MyProgressDialog.build(context, message);
            }
            dialog.setMessage(message);
            dialog.show();
        }
    }

    @Override
    public void onComplete() {
        if (dialog != null) {
            dialog.dismiss();
        }
        onSubscriberCompleted();
    }


    @Override
    public void onError(Throwable e) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (e instanceof RequestException){
            onSubscriberError(e.getMessage());
        }else {
            e.printStackTrace();
            onSubscriberError("很抱歉，操作失败");
        }




    }

    public  void onSubscriberCompleted(){};
    public abstract void onSubscriberError(String errorMsg);
}
