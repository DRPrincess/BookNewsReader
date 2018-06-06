package com.baseres;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


public class MyProgressDialog extends Dialog {

    private TextView tv_message;
    private CharSequence message;

    protected MyProgressDialog(Context context, int theme){
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        tv_message = (TextView) findViewById(R.id.tv_loading);// 提示文字
        if (tv_message != null) {
            if (!TextUtils.isEmpty(message)) {
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(message);
            } else {
                tv_message.setVisibility(View.GONE);
            }
        }
    }

    public static MyProgressDialog build(Context context, CharSequence message) {
        return build(context, message, true, null);
    }

    public static MyProgressDialog build(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        MyProgressDialog dialog = new MyProgressDialog(context, R.style.DialogLoading);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        return dialog;
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }
}
