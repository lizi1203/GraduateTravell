package com.example.graduatetravell.Relax;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RelaxDetailActivity extends AppCompatActivity {

    private String detailID;
    private Handler handler;

    //recyclerView部分数据
    private ListView detailListView;
    private  RelaxDetailAdapter adapter;;
    ArrayList<RelaxDetailHeadItemModal> relaxDetailHeadItemModals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent detailIntent = getIntent();
        detailID = detailIntent.getStringExtra("detailID");
        getDetailData(detailID);

        detailListView = findViewById(R.id.relax_detail_listView);
        adapter = new RelaxDetailAdapter(this,R.layout.relax_detail_head,relaxDetailHeadItemModals);
        adapter.notifyDataSetChanged();
        detailListView.setAdapter(adapter);

        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 5)
                {
                    relaxDetailHeadItemModals.addAll((ArrayList<RelaxDetailHeadItemModal>) msg.obj);
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
                String url = "http://api.breadtrip.com/v2/new_trip/spot/?spot_id="+detailID;

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
                        JsonObject data = jsonObject.get("data").getAsJsonObject();


                        RelaxDetailBean relaxDetailBean = new Gson().fromJson(data,RelaxDetailBean.class);
                        //对象中拿到集合
                        RelaxDetailBean.SpotBean relaxSpotBean = relaxDetailBean.getSpot();
                        RelaxDetailBean.tripBean relaxTripBean = relaxDetailBean.getTrip();

                        ArrayList<RelaxDetailHeadItemModal> tempItemModals = new ArrayList<>();
                        RelaxDetailHeadItemModal newModal = new RelaxDetailHeadItemModal(relaxSpotBean.getText(),relaxTripBean.getUser().getAvatar_l(),0,relaxTripBean.getUser().getName(),relaxSpotBean.getCover_image());
                        tempItemModals.add(newModal);
                        for(RelaxDetailBean.DetailBean detailBean : relaxSpotBean.getDetail_list()){
                            newModal = new RelaxDetailHeadItemModal(detailBean.getText(),detailBean.getPhoto(),1,null,null);
                            tempItemModals.add(newModal);
                        }
                        //插入评论区头部
                        tempItemModals.add(new RelaxDetailHeadItemModal(String.valueOf(relaxSpotBean.getComments().size()),null,3,null,null));
                        for(RelaxDetailBean.CommentBean commentBean : relaxSpotBean.getComments()){
                            String comment = commentBean.getUser().getName()+" : "+commentBean.getComment();
                            newModal = new RelaxDetailHeadItemModal(comment,commentBean.getUser().getAvatar_l(),2,commentBean.getDate_added(),null);
                            tempItemModals.add(newModal);
                        }


                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 5;
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