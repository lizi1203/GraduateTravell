package com.example.graduatetravell.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private Context context;
    private ArrayList<NewsListItemModal> newsListItemModalArrayList = new ArrayList<>();

    public NewsAdapter(Context context, ArrayList<NewsListItemModal> newsListItemModalArrayList) {
        this.context = context;
        this.newsListItemModalArrayList = newsListItemModalArrayList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_news_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        NewsListItemModal newsItem = newsListItemModalArrayList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.author.setText(newsItem.getAuthor());
        holder.readTime.setText(newsItem.getReadTime());
        Glide.with(context).load(newsItem.getNewsImage()).into(holder.newsImage);
    }


    @Override
    public int getItemCount() {
        return newsListItemModalArrayList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        //标题
        private TextView title;
        //作者
        private TextView author;
        //阅读时长
        private TextView readTime;
        //图片
        private ImageView newsImage;

        public NewsHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            author = (TextView) itemView.findViewById(R.id.news_author);
            readTime = itemView.findViewById(R.id.news_readTime);
        }

    }
}
