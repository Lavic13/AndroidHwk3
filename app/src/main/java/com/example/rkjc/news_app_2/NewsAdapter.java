package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import  com.example.rkjc.news_app_2.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {
    Context mContext;
    private ArrayList<NewsItem> mNews;

    public  NewsAdapter(Context context, ArrayList<NewsItem> news){
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView title;
        TextView description;

        public NewsItemViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.news_date);
            title = (TextView) itemView.findViewById(R.id.news_title);
            description = (TextView) itemView.findViewById(R.id.news_description);
        }

        void bind(final int listIndex){
            date.setText(mNews.get(listIndex).getPublishedAt());
            title.setText((mNews.get(listIndex).getTitle()));
            description.setText(mNews.get(listIndex).getDescription());
        }
    }


}
