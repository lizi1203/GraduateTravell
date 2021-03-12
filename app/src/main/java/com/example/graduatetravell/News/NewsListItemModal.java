package com.example.graduatetravell.News;

import android.widget.ImageView;
import android.widget.TextView;

public class NewsListItemModal {

    //标题
    private String title;
    //作者
    private String author;
    //阅读时长
    private String readTime;
    //图片
    private String newsImage;

    public NewsListItemModal(String title, String author, String readTime, String newsImage) {
        this.title = title;
        this.author = author;
        this.readTime = readTime;
        this.newsImage = newsImage;
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

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }
}
