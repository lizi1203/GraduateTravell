package com.example.graduatetravell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graduatetravell.Story.StoryRecyclerItemModal;
import com.example.graduatetravell.Story.StoryResultBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        //注册点击事件
        Button goToRegister_button = (Button)findViewById(R.id.goToRegister_button);
        goToRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentToRegister);
            }
        });

        //登录点击事件
        Button login_Button = (Button)findViewById(R.id.login_button);
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取编辑框中输入的姓名和密码
                EditText userNameText = findViewById(R.id.username_editText);
                EditText passwordText = findViewById(R.id.password_editText);
                String username = userNameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                String path = "http://10.0.2.2:8080/AndroidTest/mustLogin?logname=" + username + "&password=" + password;
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .connectTimeout(5000, TimeUnit.MILLISECONDS)
                                        .readTimeout(5000, TimeUnit.MILLISECONDS)
                                        .build();//创建OkHttpClient对象
                                Request request = new Request.Builder()
                                        .url(path)//请求接口。如果需要传参拼接到接口后面。
                                        .build();//创建Request 对象
                                Response response = null;
                                response = client.newCall(request).execute();//得到Response 对象
                                    String responseData = response.body().string();
                                    if (responseData.equals("login successfully!\r\n")) {
                                        Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intentToMain);
                                    }else
                                    {
                                        Looper.prepare();
                                        Toast.makeText(LoginActivity.this,"用户名或密码错误!",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }

                            } catch (MalformedURLException e) {
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }



}
