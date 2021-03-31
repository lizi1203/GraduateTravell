package com.example.graduatetravell.Mine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.EditBean;
import com.example.graduatetravell.Story.StoryDetailAdapter;
import com.example.graduatetravell.Story.StoryDetailBean;
import com.example.graduatetravell.Story.StoryDetailItemModal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MineNoteDetailActivity extends AppCompatActivity {
    private String editJson;

    //ListView部分数据
    private ListView detailListView;
    private MineNoteDetailAdapter adapter;;
    private ArrayList<EditBean> mineNoteDetailItemModals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_note_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        editJson = intent.getStringExtra("editJson");
        //将JSON的String 转成一个JsonArray对象
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(editJson).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<EditBean> editBeanList = new ArrayList<>();
        //加强for循环遍历JsonArray

        for (JsonElement edit : jsonArray) {
            //使用GSON，直接转成Bean对象
            EditBean editBean = gson.fromJson(edit, EditBean.class);
            mineNoteDetailItemModals.add(editBean);
        }

        detailListView = findViewById(R.id.minenote_detail_listView);
        adapter = new MineNoteDetailAdapter(this,R.layout.story_detail_base,mineNoteDetailItemModals);
        adapter.notifyDataSetChanged();
        detailListView.setAdapter(adapter);

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