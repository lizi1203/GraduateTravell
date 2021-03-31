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
import com.example.graduatetravell.Story.StoryDetailActivity;


import java.util.ArrayList;

public class MineNoteAdpter extends RecyclerView.Adapter<MineNoteAdpter.MyHolder>{
    private Context context;
    private ArrayList<MineNoteRecyclerModal> mineNoteRecyclerModals;
    private UserNameApplication app;

    public MineNoteAdpter(Context context, ArrayList<MineNoteRecyclerModal> mineNoteRecyclerModals) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mineNoteRecyclerModals = mineNoteRecyclerModals;//实体类数据ArrayList

    }

    @NonNull
    @Override
    public MineNoteAdpter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.cardview_base_item, null);
        //返回到Holder
        return new MineNoteAdpter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineNoteAdpter.MyHolder holder, int position) {
        if(holder instanceof MineNoteAdpter.MyHolder) {
            //根据点击位置绑定数据
            MineNoteRecyclerModal data = mineNoteRecyclerModals.get(position);
            //        holder.mItemGoodsImg;
            holder.title.setText(data.getTitle());//获取实体类中的name字段并设置
            holder.author.setText(data.getUserName());//获取实体类中的breif字段并设置
            Glide.with(context).load(data.getImage()).into(holder.mainImage);
            if(data.getUserHead()!=null) {
                Glide.with(context).load(data.getUserHead()).into(holder.headImage);
            }
            else{
                Glide.with(context).load(R.drawable.head).into(holder.headImage);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent;
                    detailIntent = new Intent(context, MineNoteDetailActivity.class);
                    detailIntent.putExtra("editJson",data.getContent());
                    context.startActivity(detailIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mineNoteRecyclerModals.size();
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
