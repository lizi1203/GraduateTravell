package com.example.graduatetravell.Manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.graduatetravell.News.NewsListItemModal;
import com.example.graduatetravell.News.NewsResultBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataManager {
    String responseData;
    ArrayList<NewsListItemModal> newsListItemModalArrayList;
    Handler handler = new Handler();

    public void baseConnection(String apiUrl) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(apiUrl)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        responseData = response.body().string();
                        parseJsonwithGson(responseData);
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.obj = newsListItemModalArrayList;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonwithGson(String responseData) {
        //GSON直接解析成对象
        NewsResultBean resultBean = new Gson().fromJson(responseData,NewsResultBean.class);
        //对象中拿到集合
        List<NewsResultBean.StoryBean> storyBeanList = resultBean.getStories();
        List<NewsResultBean.StoryBeanT> storyBeanTList = resultBean.getTop_stories();
        newsListItemModalArrayList = new ArrayList<>();
        for(NewsResultBean.StoryBean storyBean : storyBeanList){
            NewsListItemModal newModal = new NewsListItemModal(storyBean.getTitle(),storyBean.getHint(),storyBean.getImages().get(0),storyBean.getUrl());
            newsListItemModalArrayList.add(newModal);
        }

        for(NewsResultBean.StoryBeanT storyBeanT : storyBeanTList){
            NewsListItemModal newModal = new NewsListItemModal(storyBeanT.getTitle(),storyBeanT.getHint(),storyBeanT.getImage(),storyBeanT.getUrl());
            newsListItemModalArrayList.add(newModal);
        }
    }

    public void NewsDataManager(String apiUrl){

        baseConnection(apiUrl);

    }
}

