package com.example.graduatetravell;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.graduatetravell.Story.EditAdapter;
import com.example.graduatetravell.Story.EditBean;
import com.example.graduatetravell.Story.LocationWebActivity;
import com.example.graduatetravell.Story.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private Button addImageButton;
    private Bitmap bm;

    private ListView mListView;
    private Button addItemButton;
    private EditAdapter mAdapter;
    private List<EditBean> mData;

    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        addImageButton = (Button)findViewById(R.id.addImage_button);
//        addImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestWritePermission();
//                Intent intentImag = new Intent();
//                intentImag = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intentImag.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intentImag,1);
//            }
//        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mListView = (ListView) findViewById(R.id.edit_listview);
        addItemButton = (Button) findViewById(R.id.addItem_button);
        mData = new ArrayList<EditBean>();
        mAdapter = new EditAdapter(this, mData);
        mAdapter.setsubClickListener(new EditAdapter.SubClickListener() {
            @Override
            public void OntopicClickListener(View v, EditBean bean, int position) {
                curPosition = position;
            }

        });
        mListView.setAdapter(mAdapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.add(new EditBean());
                mAdapter.notifyDataSetChanged();
                requestWritePermission();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.ok_button:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plus_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //询问权限
    private void requestWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageIntent) {
        super.onActivityResult(requestCode, resultCode, imageIntent);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK
                && imageIntent != null){
            Toast.makeText(this, "图片已选择", Toast.LENGTH_SHORT).show();

            Uri selectedImage = imageIntent.getData();

            String[] filePathColumns = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePathColumns[0]);

            String imagePath = c.getString(columnIndex);

            showImage(imagePath,curPosition);

            c.close();

        }
    }

    private void showImage(String imagePath,int postion) {
        bm = BitmapFactory.decodeFile(imagePath);

        mData.get(postion).setImagePath(bm);
        mAdapter.notifyDataSetChanged();
    }
}