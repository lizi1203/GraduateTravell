package com.example.graduatetravell;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 4)
                {
                    Toast.makeText(RegisterActivity.this,"注册成功，返回登陆",Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
            }

        };

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
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
                                String path = "http://10.0.2.2:8080/AndroidTest/mustRegister?logname=" + username + "&password=" + password;
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
                                if (responseData.equals("register successfully!\r\n")) {
                                    //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                                    Message message = new Message();
                                    message.what = 4;
                                    message.obj = responseData;
                                    handler.sendMessage(message);
                                    Intent intentToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intentToLogin);
                                }else if(responseData.equals("username is existed!\r\n"))
                                {
                                    Looper.prepare();
                                    Toast.makeText(RegisterActivity.this,"用户名已被注册！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else{
                                    Looper.prepare();
                                    Toast.makeText(RegisterActivity.this,"信息输入错误!",Toast.LENGTH_SHORT).show();
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
