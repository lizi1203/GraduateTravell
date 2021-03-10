package com.example.graduatetravell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                //SendByHttpClient(username,password);
                Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentToMain);
            }
        });
    }


}
