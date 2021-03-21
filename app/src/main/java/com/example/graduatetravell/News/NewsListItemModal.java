package com.example.graduatetravell.News;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.io.Serializable;

public class NewsListItemModal implements Serializable {

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

    //重写equals方法
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewsListItemModal other = (NewsListItemModal) obj;
        if (!title.equals(other.title))
            return false;
        if (!author.equals(other.author))
            return false;
        if (!newsImage.equals(other.newsImage))
            return false;
        if (!url.equals(other.url))
            return false;


        return true;
    }
}
