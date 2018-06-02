package com.south.worker.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context mContext;
    List<NewsBean> mDatas;


    public HomeAdapter(Context context, List<NewsBean> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_news, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsBean bean = mDatas.get(position);
        if(bean != null){
            holder.tvNewsTitle.setText(bean.title);
            holder.ivNewsImage.setImageResource(bean.image);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas == null || mDatas.isEmpty() ? 0 : mDatas.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivNewsImage)
        ImageView ivNewsImage;
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
