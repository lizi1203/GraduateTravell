package com.example.graduatetravell.Mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.News.NewsAdapter;
import com.example.graduatetravell.News.NewsListItemModal;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;
import com.example.graduatetravell.Story.WebActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HistoryNewsAdapter extends RecyclerView.Adapter<HistoryNewsAdapter.NewsHolder>{

    private Context context;
    private ArrayList<NewsListItemModal> newsListItemModalArrayList = new ArrayList<>();
    private String fileName;

    private UserNameApplication app;

    //用于存储读取的历史数据
    private ArrayList<NewsListItemModal> tempRecyclerItemModalList = new ArrayList<NewsListItemModal>();
    //用于重装历史数据
    private ArrayList<NewsListItemModal> revertRecyclerItemModalList = new ArrayList<NewsListItemModal>();

    public HistoryNewsAdapter(Context context, ArrayList<NewsListItemModal> newsListItemModalArrayList , String filename) {
        this.context = context;
        this.newsListItemModalArrayList = newsListItemModalArrayList;
        this.fileName = filename;
    }

    @NonNull
    @Override
    public HistoryNewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_news_item, parent, false);
        return new HistoryNewsAdapter.NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryNewsAdapter.NewsHolder holder, int position) {
        NewsListItemModal newsItem = newsListItemModalArrayList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.author.setText(newsItem.getAuthor());
        Glide.with(context).load(newsItem.getNewsImage()).into(holder.newsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(context, WebActivity.class);
                webIntent.putExtra("url",newsItem.getUrl());

                //将点击的Item数据写入文件
                whriteToFile(newsItem);
                context.startActivity(webIntent);
            }
        });
    }

    private void whriteToFile(NewsListItemModal data) {
        String path = context.getFilesDir().getAbsolutePath() ;
        app = (UserNameApplication) context.getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        if(!file.exists()){
            file.mkdirs() ;
        }
        File file2 = new File(file.getAbsoluteFile()  + "/" +fileName) ;
        //先读取原有的历史数据
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
            tempRecyclerItemModalList = (ArrayList<NewsListItemModal>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //倒转历史数据
        revertRecyclerItemModalList.add(data);
        for(NewsListItemModal newsRecyclerItemModal:tempRecyclerItemModalList){
            if(newsRecyclerItemModal.equals(data)) {

            }else{
                revertRecyclerItemModalList.add(newsRecyclerItemModal);
            }
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file2));
            objectOutputStream.writeObject(revertRecyclerItemModalList);
            objectOutputStream.close() ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
        //图片
        private ImageView newsImage;

        public NewsHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            author = (TextView) itemView.findViewById(R.id.news_author);
        }

    }
}
