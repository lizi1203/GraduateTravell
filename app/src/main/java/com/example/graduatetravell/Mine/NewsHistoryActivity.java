package com.example.graduatetravell.Mine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.News.NewsAdapter;
import com.example.graduatetravell.News.NewsListItemModal;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NewsHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsListItemModal> newsListItemModalArrayList = new ArrayList<>();
    private HistoryNewsAdapter adapter;
    private UserNameApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_history);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String path = getFilesDir().getAbsolutePath();
        app = (UserNameApplication) getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        File file2 = new File(file.getAbsoluteFile()  + "/NewsHistory.txt") ;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
            newsListItemModalArrayList = (ArrayList<NewsListItemModal>) objectInputStream.readObject();
            adapter.notifyDataSetChanged();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置recyclerView属性
        recyclerView = findViewById(R.id.news_history_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryNewsAdapter(this, newsListItemModalArrayList ,"NewsHistory.txt");
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}