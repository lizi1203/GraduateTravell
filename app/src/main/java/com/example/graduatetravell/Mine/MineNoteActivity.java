package com.example.graduatetravell.Mine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.GridSpacingItemDecoration;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MineNoteActivity extends AppCompatActivity {

    //recyclerView部分数据
    private RecyclerView recyclerView;
    private ArrayList<MineNoteRecyclerModal> mineNoteRecyclerModals = new ArrayList<MineNoteRecyclerModal>();
    private MineNoteAdpter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserNameApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_note);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String path = getFilesDir().getAbsolutePath();
        app = (UserNameApplication) getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        File file2 = new File(file.getAbsoluteFile()  + "/MineNote.txt") ;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
            mineNoteRecyclerModals = (ArrayList<MineNoteRecyclerModal>) objectInputStream.readObject();
            adapter.notifyDataSetChanged();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        recyclerView = (RecyclerView)findViewById(R.id.minenote_recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge,true));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MineNoteAdpter(this, mineNoteRecyclerModals);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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