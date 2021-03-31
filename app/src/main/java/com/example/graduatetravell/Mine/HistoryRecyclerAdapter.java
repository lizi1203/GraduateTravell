package com.example.graduatetravell.Mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Relax.RelaxDetailActivity;
import com.example.graduatetravell.Story.StoryDetailActivity;
import com.example.graduatetravell.Story.StoryFragment;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyHolder>{

    private Context context;
    private ArrayList<StoryRecyclerItemModal> storyRecyclerItemModalList;

    private UserNameApplication app;

    //用于存储读取的历史数据
    private ArrayList<StoryRecyclerItemModal> tempRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;
    //用于重装历史数据
    private ArrayList<StoryRecyclerItemModal> revertRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;

    private String fileName ;

    public HistoryRecyclerAdapter(Context context, ArrayList<StoryRecyclerItemModal> storyRecyclerItemModalList,String fileName) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.storyRecyclerItemModalList = storyRecyclerItemModalList;//实体类数据ArrayList
        this.fileName = fileName;


        String path = context.getFilesDir().getAbsolutePath() ;
        app = (UserNameApplication) context.getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        if(!file.exists()){
            file.mkdirs() ;
        }
        File file2 = new File(file.getAbsoluteFile()  + "/" +fileName) ;
        //先读取原有的历史数据
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(context.openFileInput(String.valueOf(file2)));
            tempRecyclerItemModalList = (ArrayList<StoryRecyclerItemModal>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public HistoryRecyclerAdapter(List<StoryRecyclerItemModal> storyRecyclerItemModals, StoryFragment storyFragment) {

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.cardview_base_item, null);
        //返回到Holder
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.MyHolder holder, int position) {

        if(holder instanceof HistoryRecyclerAdapter.MyHolder) {
            //根据点击位置绑定数据
            StoryRecyclerItemModal data = storyRecyclerItemModalList.get(position);
            //        holder.mItemGoodsImg;
            holder.title.setText(data.getItemTitle());//获取实体类中的name字段并设置
            holder.author.setText(data.getItemAuthor());//获取实体类中的breif字段并设置
            Glide.with(context).load(data.getIconURL()).into(holder.mainImage);
            Glide.with(context).load(data.getItemHeadURL()).into(holder.headImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent;
                    if(fileName.equals("StoryHistory.txt")) {
                        detailIntent = new Intent(context, StoryDetailActivity.class);
                        detailIntent.putExtra("detailID", data.getDetailID());
                        detailIntent.putExtra("title", data.getItemTitle());
                    }
                    else{
                        detailIntent = new Intent(context, RelaxDetailActivity.class);
                        detailIntent.putExtra("detailID",data.getDetailID());
                    }
                    //将点击的Item数据写入文件
                    whriteToFile(data);
                    context.startActivity(detailIntent);
                }
            });
        }
    }

    private void whriteToFile(StoryRecyclerItemModal data) {
        String path = context.getFilesDir().getAbsolutePath() ;
        app = (UserNameApplication) context.getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        if(!file.exists()){
            file.mkdirs() ;
        }
        File file2 = new File(file.getAbsoluteFile()  + "/" +fileName) ;
        //先读取原有的历史数据
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
            tempRecyclerItemModalList = (ArrayList<StoryRecyclerItemModal>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //倒转历史数据
        revertRecyclerItemModalList.add(data);
        for(StoryRecyclerItemModal storyRecyclerItemModal:tempRecyclerItemModalList){
            if(storyRecyclerItemModal.equals(data)) {

            }else{
                revertRecyclerItemModalList.add(storyRecyclerItemModal);
            }
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file2));
            objectOutputStream.writeObject(revertRecyclerItemModalList);
            objectOutputStream.close() ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return storyRecyclerItemModalList.size();
    }

    /**
     * 给控件绑定布局
     */
    public class MyHolder extends RecyclerView.ViewHolder
    {

        private TextView title;
        private ImageView mainImage;
        private TextView author;
        private ImageView headImage;

        public MyHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            mainImage = (ImageView) itemView.findViewById(R.id.card_image);
            author = (TextView) itemView.findViewById(R.id.card_name);
            headImage = itemView.findViewById(R.id.card_head_image);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
        }
    }
}
