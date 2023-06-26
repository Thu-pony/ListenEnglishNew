package com.example.listennewsforenglish;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author Liang
 * @date 2023/6/19 20:49
 * description
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<NewsBean> mDatas;
    private final String TAG = "DailyVOA";
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView getMtitle() {
            return mtitle;
        }

        public void setMtitle(TextView mtitle) {
            this.mtitle = mtitle;
        }

        public ImageView getmCover() {
            return mCover;
        }

        public void setmCover(ImageView mCover) {
            this.mCover = mCover;
        }

        public TextView getmContent() {
            return mContent;
        }

        public void setmContent(TextView mContent) {
            this.mContent = mContent;
        }

        private TextView mtitle;
        private ImageView mCover;
        private TextView mContent;
        private MyItemOnclickListner mListner;
        public ViewHolder(View view,MyItemOnclickListner myItemOnclickListner) {
            super(view);
            mtitle = (TextView) view.findViewById(R.id.news_title);
            mCover = (ImageView) view.findViewById(R.id.news_cover);
            mContent = (TextView) view.findViewById(R.id.news_content);
            this.mListner = myItemOnclickListner;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                if (mListner != null) {
                    mListner.onItemClick(v,getAdapterPosition());
                }
        }
        public interface MyItemOnclickListner{
            public void onItemClick(View view, int postition);
        }
    }

    public NewsAdapter(ArrayList<NewsBean> arrayList) {
        mDatas = arrayList;
    }
    private ViewHolder.MyItemOnclickListner mListner;
    public void setMyItemOnclickListner(ViewHolder.MyItemOnclickListner myItemOnclickListner) {
        mListner = myItemOnclickListner;
    }
    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view,mListner);
    }



    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.getMtitle().setText(mDatas.get(position).getNewsTitle());
        holder.getmCover().setImageResource(mDatas.get(position).getNewsCover());
        holder.getmContent().setText("see more deatiled infromation *****");

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
