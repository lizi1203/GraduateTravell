package com.example.graduatetravell.News;

import android.widget.ImageView;
import android.widget.TextView;

public class NewsListItemModal {

    //标题
    private String title;
    //作者
    private String author;

    //图片
    private String newsImage;
    //网址
    private String url;

    public NewsListItemModal(String title, String author, String newsImage,String url) {
        this.title = title;
        this.author = author;
        this.newsImage = newsImage;
        this.url = url;
    }

    public NewsListItemModal() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
