package com.example.graduatetravell.Story;

import java.util.List;

public class SqlReturnBean {
    private String username;
    private String title;
    private String cover_image_default;
    private String userhead;
    private String content;
    private long noteID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getNoteID() {
        return noteID;
    }

    public void setNoteID(long noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_image_default() {
        return cover_image_default;
    }

    public void setCover_image_default(String cover_image_default) {
        this.cover_image_default = cover_image_default;
    }

    public String getUserhead() {
        return userhead;
    }

    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }
}
