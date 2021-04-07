package com.example.graduatetravell.Story;

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
import com.example.graduatetravell.Mine.MineNoteDetailActivity;
import com.example.graduatetravell.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.MyHolder> {

    private Context context;
    private ArrayList<StoryRecyclerItemModal> storyRecyclerItemModalList;
    //用于存储读取的历史数据
    private ArrayList<StoryRecyclerItemModal> tempRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;
    //用于重装历史数据
    private ArrayList<StoryRecyclerItemModal> revertRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    private UserNameApplication app;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    //根据pos返回不同的ItemViewType
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    public StoryRecyclerAdapter(Context context, ArrayList<StoryRecyclerItemModal> storyRecyclerItemModalList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.storyRecyclerItemModalList = storyRecyclerItemModalList;//实体类数据ArrayList
    }



    public StoryRecyclerAdapter(List<StoryRecyclerItemModal> storyRecyclerItemModals, StoryFragment storyFragment) {

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new MyHolder(mHeaderView);
        View view = View.inflate(context,R.layout.cardview_base_item, null);
        //返回到Holder
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(holder);
        if(holder instanceof MyHolder) {
            //根据点击位置绑定数据
            StoryRecyclerItemModal data = storyRecyclerItemModalList.get(pos);
            //        holder.mItemGoodsImg;
            holder.title.setText(data.getItemTitle());//获取实体类中的name字段并设置
            holder.author.setText(data.getItemAuthor());//获取实体类中的breif字段并设置
            Glide.with(context).load(data.getIconURL()).into(holder.mainImage);
            if(data.getItemHeadURL()!=null) {
                Glide.with(context).load(data.getItemHeadURL()).into(holder.headImage);
            }else{
                Glide.with(context).load(R.drawable.head).into(holder.headImage);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.getDetailID()!=null) {//有详情页ID的
                        Intent detailIntent = new Intent(context, StoryDetailActivity.class);
                        detailIntent.putExtra("detailID", data.getDetailID());
                        detailIntent.putExtra("title", data.getItemTitle());

                        //将点击的Item数据写入文件
                        whriteToFile(data);
                        context.startActivity(detailIntent);
                    }
                    else{//有数据库content的
                        Intent detailIntent = new Intent(context, MineNoteDetailActivity.class);
                        detailIntent.putExtra("editJson",data.getContent());
                        detailIntent.putExtra("username",data.getItemAuthor());

                        //将点击的Item数据写入文件
                        whriteToFile(data);
                        context.startActivity(detailIntent);
                    }
                }
            });
        }
    }

    private void whriteToFile(StoryRecyclerItemModal data) {
        BufferedWriter writer ;
        String path = context.getFilesDir().getAbsolutePath() ;
        app = (UserNameApplication) context.getApplicationContext(); //获取应用程序
        File file = new File(path + "/" + app.getUserName()) ;
        if(!file.exists()){
            file.mkdirs() ;
        }
        File file2 = new File(file.getAbsoluteFile()  + "/StoryHistory.txt") ;
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

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? storyRecyclerItemModalList.size() : storyRecyclerItemModalList.size() + 1;
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
            if(itemView == mHeaderView)
                return;
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
                    return getItemViewType(position) == TYPE_HEADER
                            ? 2 : 1;
                }
            });
        }
    }

}
