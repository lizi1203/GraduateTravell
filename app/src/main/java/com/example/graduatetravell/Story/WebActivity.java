package com.example.graduatetravell.Story;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.graduatetravell.R;

import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private ArrayList<String> webURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent webIntent = getIntent();
        int position = webIntent.getIntExtra("position",0);
        webURL = new ArrayList<String>(3);
        webURL.add("https://bbs.qyer.com/thread-3469562-1.html");
        webURL.add("https://bbs.qyer.com/thread-3522102-1.html");
        webURL.add("https://bbs.qyer.com/thread-3523739-1.html");

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);//设置适应Html5 重点是这个设置

        //访问网页
        webView.loadUrl(webURL.get(position));
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}