package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.rkjc.news_app_2.Database.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {
    Context mContext;
    List<NewsItem> mNews;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener{
      void onListItemClick(String url);
    }

    public NewsAdapter(Context context, ArrayList<NewsItem> news, ListItemClickListener listener){
        this.mContext = context;
        this.mNews = news;
        this.mOnClickListener = listener;
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
        if(null == mNews) return 0;
        return mNews.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date;
        TextView title;
        TextView description;

        public NewsItemViewHolder(View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.news_date);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            itemView.setOnClickListener(this);
        }

        void bind(final int listIndex){
            date.setText("Date: " + mNews.get(listIndex).getPublishedAt());
            title.setText(("Title: "+ mNews.get(listIndex).getTitle()));
            description.setText("Description: " + mNews.get(listIndex).getDescription());
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            String url_to_web_page = mNews.get(adapterPosition).getUrl();
            mOnClickListener.onListItemClick(url_to_web_page);
        }
    }


}
