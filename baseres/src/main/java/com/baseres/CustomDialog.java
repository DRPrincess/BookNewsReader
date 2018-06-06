package com.baseres;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baselib.utils.OptAnimationLoader;


/**
 *
 *描述   ：
 *
 *作者   ：Created by DR on 2018/6/7.
 */

public class CustomDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private View mDialogView, lineView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private TextView mcontentTextView;
    private TextView mTitleTextView;
    private TextView cstoumDialogMsgContentTitle;
    private String mContentText;
    private boolean mShowRight = false;
    private LinearLayout mTitleView;
    private String mTitleText;
    private String mContentTitle;
    private String mRightText;
    private String mLeftText;
    private Button mLeftButton;
    private Button mRightButton;
    private OnCustomDialogClickListener mRightClickListener;
    private OnCustomDialogClickListener mLeftClickListener;
    private boolean mCloseFromCancel;
    public static final int NORMAL_TYPE = 0;

    public interface OnCustomDialogClickListener {
        void onClick(CustomDialog customDialog);
    }

    public CustomDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    public CustomDialog(Context context, int alertType) {

        super(context, R.style.custom_dialog);
        mContext = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            CustomDialog.super.cancel();
                        } else {
                            CustomDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        lineView = findViewById(R.id.customLineView);
        mTitleView = (LinearLayout) findViewById(R.id.cstoumDialogMsgTitleView);
        mTitleView.setVisibility(View.GONE);
        mTitleTextView = (TextView) findViewById(R.id.cstoumDialogMsgTitle);
        cstoumDialogMsgContentTitle = (TextView) findViewById(R.id.cstoumDialogMsgContentTitle);
        cstoumDialogMsgContentTitle.setVisibility(View.GONE);
        mcontentTextView = (TextView) findViewById(R.id.cstoumDialogMsg);
        mcontentTextView.setVisibility(View.GONE);
        mLeftButton = (Button) findViewById(R.id.cstoumDialogLeftBtn);
        mRightButton = (Button) findViewById(R.id.cstoumDialogRightBtn);
        // mRightButton.setVisibility(View.GONE);
        mLeftButton.setOnClickListener(this);
        mRightButton.setOnClickListener(this);
        mRightButton.setVisibility(View.GONE);
        setContentText(mContentText);
        setRightText(mRightText);
        setLeftText(mLeftText);
        setTitleText(mTitleText);
        setContentTitleText(mContentTitle);
    }

    public String getContentText() {
        return mContentText;
    }

    public CustomDialog setContentText(String text) {
        mContentText = text;
        if (mcontentTextView != null && mContentText != null) {
            mcontentTextView.setVisibility(View.VISIBLE);
            if (mContentText.contains("&MB")) {
                String tempstr[] = mContentText.split("&MB");

                SpannableString msp = new SpannableString(tempstr[0]);
                int startindex = mContentText.indexOf(tempstr[1]);
                int endindex = startindex + tempstr[1].length();
                msp.setSpan(new ForegroundColorSpan(Color.RED), startindex, endindex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色
                mcontentTextView.setText(msp);
            } else {
                mcontentTextView.setText(mContentText);
            }

        }
        return this;
    }

    public boolean isShowRightButton() {
        return mShowRight;
    }

    public CustomDialog showRightButton(boolean isShow) {
        mShowRight = isShow;
        if (mRightButton != null) {
            mRightButton.setVisibility(mShowRight ? View.VISIBLE : View.GONE);
            lineView.setVisibility(mShowRight ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getRightText() {
        return mRightText;
    }

    public CustomDialog setRightText(String text) {
        mRightText = text;
        if (mRightButton != null && mRightText != null) {
            showRightButton(true);
            mRightButton.setText(mRightText);
        }
        return this;
    }

    public String getLeftText() {
        return mLeftText;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public String getContentTitleText() {
        return mContentTitle;
    }

    public CustomDialog setLeftText(String text) {
        mLeftText = text;
        if (mLeftButton != null && mLeftText != null) {
            mLeftButton.setText(mLeftText);
        }
        return this;
    }

    public CustomDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public CustomDialog setContentTitleText(String text) {
        mContentTitle = text;
        if (cstoumDialogMsgContentTitle != null && mContentTitle != null) {
            cstoumDialogMsgContentTitle.setVisibility(View.VISIBLE);
            cstoumDialogMsgContentTitle.setText(mContentTitle);
        }
        return this;
    }

    public CustomDialog setContentTitleText(int text) {
        mContentTitle = mContext.getResources().getText(text).toString();
        if (cstoumDialogMsgContentTitle != null && mContentTitle != null) {
            cstoumDialogMsgContentTitle.setVisibility(View.VISIBLE);
            cstoumDialogMsgContentTitle.setText(mContentTitle);
        }
        return this;
    }

    public CustomDialog setRightClickListener(OnCustomDialogClickListener listener) {
        mRightClickListener = listener;
        return this;
    }

    public CustomDialog setLeftClickListener(OnCustomDialogClickListener listener) {
        mLeftClickListener = listener;
        return this;
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cstoumDialogRightBtn) {
            if (mRightClickListener != null) {
                mRightClickListener.onClick(CustomDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.cstoumDialogLeftBtn) {
            if (mLeftClickListener != null) {
                mLeftClickListener.onClick(CustomDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

}