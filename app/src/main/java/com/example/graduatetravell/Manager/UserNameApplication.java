package com.example.graduatetravell.Manager;

import android.app.Application;

public class UserNameApplication extends Application {

    private String userName;
    @Override
    public void onCreate() {
        super.onCreate();
        setUserName(NAME); //初始化全局变量
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String name) {
        this.userName = name;
    }
    private static final String NAME = "MyApplication";
}
