package com.south.worker.ui.my_part;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.constant.NetConfig;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.user_info.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartAdapter extends RecyclerView.Adapter<MyPartAdapter.ViewHolder> {

    Context mContext;
    List<PartActivityBean> mDatas;



    public MyPartAdapter(Context context, List<PartActivityBean> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_part_activities, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PartActivityBean bean = mDatas.get(position);

        if(bean != null){
            holder.tvTitle.setText(bean.Title);
            holder.tvContent.setText(bean.ViceTitle);


            if (!TextUtils.isEmpty(bean.Pic)) {
                bean.Pic = NetConfig.IMAGE_PREFIXX + bean.Pic;
                GlideApp
                        .with(mContext)
                        .load(bean.Pic)
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .into(holder.ivImage);

            } else {
                holder.ivImage.setImageResource(R.drawable.list_default);
            }
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
