package com.example.graduatetravell.Story;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.graduatetravell.R;
import com.example.graduatetravell.Relax.RelaxDetailAdapter;
import com.example.graduatetravell.Relax.RelaxDetailBean;
import com.example.graduatetravell.Relax.RelaxDetailHeadItemModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoryDetailActivity extends AppCompatActivity {

    private String detailID;
    private String title;
    private Handler handler;

    //recyclerView部分数据
    private ListView detailListView;
    private StoryDetailAdapter adapter;;
    ArrayList<StoryDetailItemModal> storyDetailItemModals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent detailIntent = getIntent();
        detailID = detailIntent.getStringExtra("detailID");
        title = detailIntent.getStringExtra("title");
        getDetailData(detailID);

        detailListView = findViewById(R.id.story_detail_listView);
        adapter = new StoryDetailAdapter(this,R.layout.story_detail_base,storyDetailItemModals);
        adapter.notifyDataSetChanged();
        detailListView.setAdapter(adapter);

        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 6)
                {
                    storyDetailItemModals.addAll((ArrayList<StoryDetailItemModal>) msg.obj);
                    adapter.notifyDataSetChanged();
                }
                else
                {

                }
            }

        };
    }

    private void getDetailData(String detailID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://api.breadtrip.com/trips/"+detailID+"/waypoints/";

                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        //拿到数组
                        JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();

                        StoryDetailBean storyDetailBean = new Gson().fromJson(jsonObject,StoryDetailBean.class);
                        //对象中拿到集合
                        StoryDetailBean.UserBean storyUserBean = storyDetailBean.getUser();
                        ArrayList<StoryDetailItemModal> tempItemModals = new ArrayList<>();
                        StoryDetailItemModal newModal = new StoryDetailItemModal(storyUserBean.getAvatar_l(),storyDetailBean.getCover_image(),"by "+storyUserBean.getName(),title,0);
                        tempItemModals.add(newModal);

                        for(StoryDetailBean.DateBean dateBean : storyDetailBean.getDays()){
                            newModal = new StoryDetailItemModal(null,null,dateBean.getDate(),dateBean.getDay(),1);
                            tempItemModals.add(newModal);
                            for(StoryDetailBean.WayPointBean wayPointBean: dateBean.getWaypoints()){
                                newModal = new StoryDetailItemModal(null,wayPointBean.getPhoto(),wayPointBean.getText(),null,2);
                                tempItemModals.add(newModal);
                            }
                        }


                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 6;
                        message.obj = tempItemModals;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //实现返回键功能
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