package com.example.graduatetravell.Mine;

import java.io.Serializable;

public class MineNoteRecyclerModal implements Serializable {
    private String userName;
    private String userHead;
    private String title;
    private String image;
    private String content;


    public MineNoteRecyclerModal(String userName, String userHead, String title, String image, String content) {
        this.userName = userName;
        this.userHead = userHead;
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
