package com.south.worker.ui.online_read;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.data.bean.ReadRankingBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class RankingListAdapter extends BaseAdapter {

    Context mContext;

    List<ReadRankingBean> mDatas;


    public RankingListAdapter(Context context, List<ReadRankingBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null || mDatas.isEmpty() ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ranking_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(position == 0){
            holder.tvOrder.setVisibility(View.GONE);
            holder.ivUserOrder.setVisibility(View.VISIBLE);
            holder.ivUserOrder.setImageResource(R.drawable.ranking_list_first);
        }else if(position == 1){
            holder.tvOrder.setVisibility(View.GONE);
            holder.ivUserOrder.setVisibility(View.VISIBLE);
            holder.ivUserOrder.setImageResource(R.drawable.ranking_list_second);
        }else  if(position == 2){
            holder.tvOrder.setVisibility(View.GONE);
            holder.ivUserOrder.setVisibility(View.VISIBLE);
            holder.ivUserOrder.setImageResource(R.drawable.ranking_list_third);
        }else{
            holder.tvOrder.setVisibility(View.VISIBLE);
            holder.ivUserOrder.setVisibility(View.GONE);

        }

        ReadRankingBean bean = mDatas.get(position);

        if(bean != null){
            holder.tvOrder.setText(String.valueOf(position+1));
            if(!TextUtils.isEmpty(bean.userName)){
                holder.tvUserName.setText(bean.userName);
            }else {
                holder.tvUserName.setText(bean.BranchName);
            }

            holder.tvUserReadTime.setText(bean.readTime +"分钟");

            if(TextUtils.isEmpty(bean.userAvater)){
                holder.ivUserAvatar.setImageResource(R.drawable.head1);
            }else{
                Glide.with(mContext).load(bean.userAvater).into(holder.ivUserAvatar);
            }
        }




        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvOrder)
        TextView tvOrder;
        @BindView(R.id.ivUserOrder)
        ImageView ivUserOrder;
        @BindView(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvUserReadTime)
        TextView tvUserReadTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
