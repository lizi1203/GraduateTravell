package com.example.graduatetravell;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.Mine.MineNoteRecyclerModal;
import com.example.graduatetravell.Story.EditAdapter;
import com.example.graduatetravell.Story.EditBean;
import com.example.graduatetravell.Story.LocationWebActivity;
import com.example.graduatetravell.Story.SqlReturnBean;
import com.example.graduatetravell.Story.StoryDetailActivity;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {

    private Button addImageButton;

    private ListView mListView;
    private Button addItemButton;
    private EditAdapter mAdapter;
    private List<EditBean> mData;

    //获取当前上传图片的item位置
    private int curPosition;
    //将recyclerview的数据转换成JSON格式
    private String editJson;
    private UserNameApplication userNameApplication;

    //用Modal将数据存入文件
    private MineNoteRecyclerModal mineNoteRecyclerModal;
    private ArrayList<MineNoteRecyclerModal> tempRecyclerItemModalList;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        userNameApplication =  (UserNameApplication) getApplicationContext();
        tempRecyclerItemModalList = new ArrayList<MineNoteRecyclerModal>();


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mListView = (ListView) findViewById(R.id.edit_listview);
        addItemButton = (Button) findViewById(R.id.addItem_button);
        mData = new ArrayList<EditBean>();
        mAdapter = new EditAdapter(this, mData);
        //监听点击操作
        mAdapter.setsubClickListener(new EditAdapter.SubClickListener() {
            @Override
            public void OntopicClickListener(View v, EditBean bean, int position) {
                curPosition = position;
                mData.get(curPosition).setEditText(bean.getEditText());
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

        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 8)
                {
                    Toast.makeText(EditActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    mineNoteRecyclerModal = new MineNoteRecyclerModal(userNameApplication.getUserName(),null,mData.get(0).getEditText(),mData.get(0).getImagePath(),editJson);
                    whriteToFile(mineNoteRecyclerModal);
                    EditActivity.this.finish();
                }
                else
                {

                }
            }

        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.ok_button:
                Gson gson = new Gson();
                editJson = gson.toJson(mData);
//                JsonParser parser = new JsonParser();
//                JsonArray jsonArray = parser.parse(editJson).getAsJsonArray();
//                for (JsonElement note : jsonArray ) {
//                    //使用GSON，直接转成Bean对象
//                    SqlReturnBean sqlReturnBean = gson.fromJson(note, SqlReturnBean.class);
//                }

                createDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void upLoadData() {
        //获取编辑框中输入的姓名和密码
        String username = userNameApplication.getUserName();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                String path = "http://10.0.2.2:8080/AndroidTest/mustUpload?username=" + username; //+ "&content=" +editJson;
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .connectTimeout(5000, TimeUnit.MILLISECONDS)
                                        .readTimeout(5000, TimeUnit.MILLISECONDS)
                                        .build();//创建OkHttpClient对象
                                //MediaType  设置Content-Type 标头中包含的媒体类型值
                                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                                        , editJson);
                                Request request = new Request.Builder()
                                        .url(path)//请求接口。如果需要传参拼接到接口后面。
                                        .post(requestBody)
                                        .build();//创建Request 对象


                                Response response = null;
                                response = client.newCall(request).execute();//得到Response 对象
                                    String responseData = response.body().string();
                                    if (responseData.equals("upload successfully!\r\n")) {
                                        Message message = new Message();
                                        message.what = 8;
                                        message.obj = responseData;
                                        handler.sendMessage(message);
                                    } else
                                    {
                                        Looper.prepare();
                                        Toast.makeText(EditActivity.this,"上传失败!",Toast.LENGTH_SHORT).show();
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

        mData.get(postion).setImagePath(imagePath);
        mAdapter.notifyDataSetChanged();
    }

    //发布前询问
    @SuppressLint("ResourceAsColor")
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您确定要发布吗？");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //传递数据到后台
                upLoadData();
                mineNoteRecyclerModal = new MineNoteRecyclerModal(userNameApplication.getUserName(),null,mData.get(0).getEditText(),mData.get(0).getImagePath(),editJson);
//                whriteToFile(mineNoteRecyclerModal);
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setIcon(R.drawable.question);
        //设置对话框颜色
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();//获取dialog布局的参数
        dialog.getWindow().setBackgroundDrawableResource(R.color.grey);

        dialog.show();
        Button btnPositive = (Button)dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.setTextColor(Color.parseColor("#FF000000"));
        Button btnNegative = (Button)dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNegative.setTextColor(Color.parseColor("#FF000000"));
    }

    private void whriteToFile(MineNoteRecyclerModal data) {
        BufferedWriter writer ;
        String path = this.getFilesDir().getAbsolutePath() ;
        userNameApplication = (UserNameApplication) this.getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + userNameApplication.getUserName()) ;
        if(!file.exists()){
            file.mkdirs() ;
        }
        File file2 = new File(file.getAbsoluteFile()  + "/MineNote.txt") ;

        //先读取原有的历史数据
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
            tempRecyclerItemModalList = (ArrayList<MineNoteRecyclerModal>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tempRecyclerItemModalList.add(data);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file2));
            objectOutputStream.writeObject(tempRecyclerItemModalList);
            objectOutputStream.close() ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}