package com.example.graduatetravell.Story;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    //ListView部分数据
    private ListView detailListView;
    private StoryDetailAdapter adapter;;
    private ArrayList<StoryDetailItemModal> storyDetailItemModals = new ArrayList<>();

    //ProgressBar部分
    private Dialog dialog;
    private ImageButton positionButton;

    //Location部分
    private String location;


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


        detailListView = findViewById(R.id.story_detail_listView);
        adapter = new StoryDetailAdapter(this,R.layout.story_detail_base,storyDetailItemModals);
        adapter.notifyDataSetChanged();
        detailListView.setAdapter(adapter);

        dialog = new ProgressDialog(StoryDetailActivity.this,ProgressDialog.THEME_HOLO_DARK);
        dialog.setTitle("正在加载");

        getDetailData(detailID);
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 6)
                {
                    storyDetailItemModals.addAll((ArrayList<StoryDetailItemModal>) msg.obj);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else
                {
                }
            }

        };


    }


    private void getDetailData(String detailID) {
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                if(detailID.length()>2) {
                    //面包旅行数据
                    url = "http://api.breadtrip.com/trips/" + detailID + "/waypoints/";
                }else {
                    //数据库数据
                    url = "http://10.0.2.2:8080/AndroidTest/mustGetNoteDetail?detailID="+detailID;
                }
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
                        location = storyDetailBean.getCities().get(0);
//                        location = location.substring(1,location.lastIndexOf("/"));
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

                    }else{
                        Looper.prepare();
                        Toast.makeText(StoryDetailActivity.this,"服务器出错",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        dialog.dismiss();
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
            case R.id.hotel_button:
                Intent intentToAirbnb = new Intent(StoryDetailActivity.this,LocationWebActivity.class);
                intentToAirbnb.putExtra("location",location);
                startActivity(intentToAirbnb);
                return true;
            case R.id.location_button:
                Intent intentToMap = new Intent(StoryDetailActivity.this,MapActivity.class);
                intentToMap.putExtra("location",location);
                startActivity(intentToMap);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_manu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}