package com.south.worker.ui.online_read;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.data.bean.MyBookBean;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.data.bean.OnlineReadBean;
import com.south.worker.data.bean.ReadThinkingBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class OnlineReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<OnlineReadBean> mDatas;


    public OnlineReadAdapter(Context context, List<OnlineReadBean> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = null;
        switch (viewType) {
            case 0:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_book_list, parent, false);
                return new OnlineBookViewHolder(convertView);
            case 1:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_read_record, parent, false);
                return new MyBookViewHolder(convertView);
            case 2:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_read_thinking_list, parent, false);
                return new MyThinkingViewHolder(convertView);


        }

        return new MyBookViewHolder(convertView);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        OnlineReadBean bean = mDatas.get(position);

        if (bean == null) {
            return;
        }

        switch (getItemViewType(position)) {

            case 0:

                OnlineBookBean bookBean = bean.mOnlineBookBean;
                Glide.with(mContext).load(bookBean.BookPic).into( ((OnlineBookViewHolder) holder).ivBooks);

                break;

            case 1:
                MyBookBean myBookBean = bean.mMyBookBean;

                ((MyBookViewHolder) holder).ivBooks.setImageResource(myBookBean.image);

                if (TextUtils.isEmpty(myBookBean.number)) {
                    ((MyBookViewHolder) holder).tvNumber.setVisibility(View.GONE);
                } else {

                    SpannableString s1 = new SpannableString("已读" + myBookBean.number + "次");
                    s1.setSpan(new ForegroundColorSpan(Color.parseColor("#f53737")), 2, s1.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((MyBookViewHolder) holder).tvNumber.setText(s1);
                }
                if (TextUtils.isEmpty(myBookBean.readTime)) {
                    ((MyBookViewHolder) holder).tvTimes.setVisibility(View.GONE);
                } else {
                    SpannableString s1 = new SpannableString(myBookBean.readTime + "分钟");
                    s1.setSpan(new AbsoluteSizeSpan(17, true), 0, s1.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    s1.setSpan(new ForegroundColorSpan(Color.parseColor("#f53737")), 0, s1.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    ((MyBookViewHolder) holder).tvTimes.setText(s1);
                }

                break;
            case 2:
                ReadThinkingBean readThinkingBean = bean.mReadThinkingBean;
                ((MyThinkingViewHolder) holder).tvThingTitle.setText(readThinkingBean.title);
                ((MyThinkingViewHolder) holder).tvThinkingContent.setText(readThinkingBean.content);
                break;


        }

    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(mDatas.get(position).type);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas == null || mDatas.isEmpty() ? 0 : mDatas.size();
    }


    static class OnlineBookViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBooks)
        ImageView ivBooks;

        public OnlineBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MyBookViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBooks)
        ImageView ivBooks;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvTimes)
        TextView tvTimes;

        public MyBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MyThinkingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvThingTitle)
        TextView tvThingTitle;
        @BindView(R.id.tvThinkingContent)
        TextView tvThinkingContent;

        public MyThinkingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
