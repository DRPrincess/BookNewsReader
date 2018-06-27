package com.south.worker.ui.online_read.read_thinking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseres.SquareImageView;
import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.data.bean.OnlineBookBean;
import com.south.worker.ui.home.HomeAdapter;
import com.south.worker.ui.user_info.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.south.worker.constant.NetConfig.IMAGE_PREFIXX;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/13.
 */

public class AllBookAdapter extends RecyclerView.Adapter<AllBookAdapter.ViewHolder> {

    Context mContext;

    List<OnlineBookBean> mDatas;

    public AllBookAdapter(Context context, List<OnlineBookBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {

        if (holder != null)
        {
            GlideApp.with(mContext).clear(holder.ivBooks);
        }
        super.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public AllBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_all_book_list, parent, false);

        ViewHolder holder = new ViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OnlineBookBean bookBean = mDatas.get(position);

        if(bookBean == null){
            return;
        }


        String url = IMAGE_PREFIXX + bookBean.BookPic;
        GlideApp
                .with(mContext)
                .load(url)
                .placeholder(R.drawable.list_default)
                .error(R.drawable.list_default)
                .into(holder.ivBooks);


        holder.ivBooks.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tvBookName.setText(String.format("《%s》",bookBean.BookName));

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBooks)
        SquareImageView ivBooks;
        @BindView(R.id.tvBookName)
        TextView tvBookName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
