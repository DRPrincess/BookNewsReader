package com.south.worker.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.constant.NetConfig;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.ui.user_info.GlideApp;

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
            holder.tvNewsTitle.setText(bean.Title);

            if(!TextUtils.isEmpty(bean.Pic)){


                String picUrl = null;

                if (holder.ivNewsImage.getTag() == null) {
                    picUrl = NetConfig.IMAGE_PREFIXX + bean.Pic;

                } else {
                    picUrl = (String) holder.ivNewsImage.getTag();
                }

                holder.ivNewsImage.setTag(null);
                GlideApp
                        .with(mContext)
                        .load(picUrl)
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .dontAnimate()
                        .into(holder.ivNewsImage);

                holder.ivNewsImage.setTag(picUrl);

            }else{
                holder.ivNewsImage.setImageResource(R.drawable.banner_default);
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
