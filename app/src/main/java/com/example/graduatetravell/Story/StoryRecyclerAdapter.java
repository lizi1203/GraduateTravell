package com.example.graduatetravell.Story;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.Mine.MineListItemModal;
import com.example.graduatetravell.R;

import java.util.ArrayList;
import java.util.List;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.MyHolder> {

    private Context context;
    private ArrayList<StoryRecyclerItemModal> storyRecyclerItemModalList;

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
        View view = View.inflate(context,R.layout.cardview_base_item, null);
        //返回到Holder
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //根据点击位置绑定数据
        StoryRecyclerItemModal data = storyRecyclerItemModalList.get(position);
        //        holder.mItemGoodsImg;
        holder.title.setText(data.getItemTitle());//获取实体类中的name字段并设置
        holder.author.setText(data.getItemAuthor());//获取实体类中的breif字段并设置
        Glide.with(context).load(data.getIconURL()).into(holder.mainImage);
        Glide.with(context).load(data.getItemHeadURL()).into(holder.headImage);
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


}
