package com.example.graduatetravell.Relax;

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
import com.example.graduatetravell.Story.StoryFragment;
import com.example.graduatetravell.Story.StoryRecyclerAdapter;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class RelaxRecyclerAdapter extends RecyclerView.Adapter<RelaxRecyclerAdapter.MyHolder>{

    private Context context;
    private ArrayList<StoryRecyclerItemModal> relaxRecyclerItemModalList;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }
    private UserNameApplication app;

    //用于存储读取的历史数据
    private ArrayList<StoryRecyclerItemModal> tempRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;
    //用于重装历史数据
    private ArrayList<StoryRecyclerItemModal> revertRecyclerItemModalList = new ArrayList<StoryRecyclerItemModal>();;

    //根据pos返回不同的ItemViewType
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    public RelaxRecyclerAdapter(Context context, ArrayList<StoryRecyclerItemModal> relaxRecyclerItemModalList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.relaxRecyclerItemModalList = relaxRecyclerItemModalList;//实体类数据ArrayList
    }



    public RelaxRecyclerAdapter(List<StoryRecyclerItemModal> relaxRecyclerItemModals, RelaxFragment relaxFragment) {

    }

    @NonNull
    @Override
    public RelaxRecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new RelaxRecyclerAdapter.MyHolder(mHeaderView);
        View view = View.inflate(context, R.layout.cardview_base_item, null);
        //返回到Holder
        return new RelaxRecyclerAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RelaxRecyclerAdapter.MyHolder holder, int position) {

        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(holder);
        if(holder instanceof RelaxRecyclerAdapter.MyHolder) {
            //根据点击位置绑定数据
            StoryRecyclerItemModal data = relaxRecyclerItemModalList.get(pos);
            //        holder.mItemGoodsImg;
            holder.title.setText(data.getItemTitle());//获取实体类中的name字段并设置
            holder.author.setText(data.getItemAuthor());//获取实体类中的breif字段并设置
            Glide.with(context).load(data.getIconURL()).into(holder.mainImage);
            Glide.with(context).load(data.getItemHeadURL()).into(holder.headImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, RelaxDetailActivity.class);
                    detailIntent.putExtra("detailID",data.getDetailID());

                    //将点击的Item数据写入文件
                    whriteToFile(data);
                    context.startActivity(detailIntent);
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
        File file2 = new File(file.getAbsoluteFile()  + "/RelaxHistory.txt") ;
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
        return mHeaderView == null ? relaxRecyclerItemModalList.size() : relaxRecyclerItemModalList.size() + 1;
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
