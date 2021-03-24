package com.example.graduatetravell.Story;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Relax.RelaxDetailAdapter;
import com.example.graduatetravell.Relax.RelaxDetailHeadItemModal;

import java.util.List;

public class StoryDetailAdapter extends ArrayAdapter<StoryDetailItemModal> {

    private int resourceId;
    private LayoutInflater inflater;



    public StoryDetailAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoryDetailItemModal storyDetailItemModal = getItem(position);//获取当前项的实例
        View viewItem1 = null;
        View viewItem2 = null;
        View viewItem3 = null;

        StoryDetailAdapter.ViewHolderHead viewHolderHead = null;
        StoryDetailAdapter.ViewHolderBase viewHolderBase = null;
        StoryDetailAdapter.ViewHolderDate viewHolderDate = null;
        int viewType =getItemViewType(position);

        if(convertView == null){
            //根据不同type设置不同布局
            switch(viewType){
                case 0:
                    viewHolderHead = new StoryDetailAdapter.ViewHolderHead();
                    viewItem1 = inflater.inflate(R.layout.story_detail_head,parent,false);
                    viewHolderHead.baseImage = viewItem1.findViewById(R.id.story_detail_back_imageView);
                    viewHolderHead.headImage = viewItem1.findViewById(R.id.story_detail_head_ImageView);
                    viewHolderHead.titleText = viewItem1.findViewById(R.id.story_detail_title_textView);
                    viewHolderHead.baseText = viewItem1.findViewById(R.id.story_detail_breif_textView);
                    viewItem1.setTag(viewHolderHead);//保存
                    convertView = viewItem1;
                    break;
                case 1:
                    viewHolderDate= new StoryDetailAdapter.ViewHolderDate();
                    viewItem3 = inflater.inflate(R.layout.story_detail_date, parent,false);
                    viewHolderDate.datetImage = viewItem3.findViewById(R.id.story_detail_date_imageView);
                    viewHolderDate.dateText = viewItem3.findViewById(R.id.story_detail_date_textView);
                    viewItem3.setTag(viewHolderDate);//保存
                    convertView = viewItem3;
                    break;
                case 2:
                    viewHolderBase = new StoryDetailAdapter.ViewHolderBase();
                    viewItem2 = inflater.inflate(R.layout.story_detail_base, parent,false);
                    viewHolderBase.baseImage = viewItem2.findViewById(R.id.story_detail_base_imageView);
                    viewHolderBase.baseText = viewItem2.findViewById(R.id.story_detail_base_textView);
                    viewItem2.setTag(viewHolderBase);//保存
                    convertView = viewItem2;
                    break;
            }

        }
        else{
            //根据不同给type设置不同holder
            switch (viewType){
                case 0:
                    viewHolderHead = ( StoryDetailAdapter.ViewHolderHead) convertView.getTag();//取出
                    break;
                case 1:
                    viewHolderDate = ( StoryDetailAdapter.ViewHolderDate) convertView.getTag();//取出
                    break;
                case 2:
                    viewHolderBase = ( StoryDetailAdapter.ViewHolderBase) convertView.getTag();//取出
            }

        }

        //根据不同type添加不同数据
        switch (viewType){
            case 0:
                Glide.with(getContext()).load(storyDetailItemModal.getBackImage()).into(viewHolderHead.baseImage);
                Glide.with(getContext()).load(storyDetailItemModal.getHeadImage()).into(viewHolderHead.headImage);
                viewHolderHead.titleText.setText(storyDetailItemModal.getText());
                viewHolderHead.baseText.setText(storyDetailItemModal.getBreifText());
                break;
            case 1:
                viewHolderDate.dateText.setText(storyDetailItemModal.getText()+" 第"+storyDetailItemModal.getBreifText()+"天");
                break;
            case 2:
                Glide.with(getContext()).load(storyDetailItemModal.getBackImage()).into(viewHolderBase.baseImage);
                viewHolderBase.baseText.setText(storyDetailItemModal.getText());
                break;
        }
        return convertView;
    }


    private class ViewHolderHead{
        public static final int  TYPE_HEAD = 0;
        ImageView baseImage;
        TextView baseText;
        ImageView headImage;
        TextView titleText;
    }

    private class ViewHolderBase{
        public static final int TYPE_BASE = 2;
        ImageView baseImage;
        TextView baseText;
    }

    private class ViewHolderDate{
        public static final int TYPE_COMMENT = 1;
        ImageView datetImage;
        TextView dateText;
    }


    @Override
    public int getItemViewType(int position){
        return this.getItem(position).getItem_type();
    }


    public class ItemType {
        public static final int ITEM_TYPE_MAX_COUNT = 3;
    }

    public int getViewTypeCount() {
        return RelaxDetailAdapter.ItemType.ITEM_TYPE_MAX_COUNT;
    }

    //设置不可点击

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
