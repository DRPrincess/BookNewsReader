package com.baseres;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 *
 *描述   ：计算比例的图片
 *
 *作者   ：Created by DR on 2018/6/4.
 */

public class SquareImageView extends AppCompatImageView {

    private float aspectRatio = 1f;

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyConfig(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyConfig(context, attrs);
    }

    public SquareImageView(Context context) {
        super(context);
    }

    private void applyConfig(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout);
        aspectRatio = a.getFloat(R.styleable.SquareLayout_ratio, 1f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int)(aspectRatio* MeasureSpec.getSize(widthMeasureSpec)), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
