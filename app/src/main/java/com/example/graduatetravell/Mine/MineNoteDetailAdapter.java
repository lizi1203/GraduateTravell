package com.example.graduatetravell.Mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Relax.RelaxDetailAdapter;
import com.example.graduatetravell.Story.EditBean;
import com.example.graduatetravell.Story.StoryDetailAdapter;
import com.example.graduatetravell.Story.StoryDetailItemModal;

import java.util.List;


public class MineNoteDetailAdapter extends ArrayAdapter<EditBean> {

    private int resourceId;
    private LayoutInflater inflater;
    private UserNameApplication app;



    public MineNoteDetailAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditBean editBean = getItem(position);//获取当前项的实例
        View viewItem1 = null;
        View viewItem2 = null;

        MineNoteDetailAdapter.ViewHolderHead viewHolderHead = null;
        MineNoteDetailAdapter.ViewHolderBase viewHolderBase = null;
        int viewType =getItemViewType(position);

        if(convertView == null){
            //根据不同type设置不同布局
            switch(viewType){
                case 0:
                    viewHolderHead = new MineNoteDetailAdapter.ViewHolderHead();
                    viewItem1 = inflater.inflate(R.layout.story_detail_head,parent,false);
                    viewHolderHead.baseImage = viewItem1.findViewById(R.id.story_detail_back_imageView);
                    viewHolderHead.headImage = viewItem1.findViewById(R.id.story_detail_head_ImageView);
                    viewHolderHead.titleText = viewItem1.findViewById(R.id.story_detail_title_textView);
                    viewHolderHead.baseText = viewItem1.findViewById(R.id.story_detail_breif_textView);
                    viewItem1.setTag(viewHolderHead);//保存
                    convertView = viewItem1;
                    break;
                case 1:
                    viewHolderBase = new MineNoteDetailAdapter.ViewHolderBase();
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
                    viewHolderHead = ( MineNoteDetailAdapter.ViewHolderHead) convertView.getTag();//取出
                    break;
                case 1:
                    viewHolderBase = ( MineNoteDetailAdapter.ViewHolderBase) convertView.getTag();//取出
            }

        }

        //根据不同type添加不同数据
        switch (viewType){
            case 0:
                Glide.with(getContext()).load(editBean.getImagePath()).into(viewHolderHead.baseImage);
                Glide.with(getContext()).load(R.drawable.head).into(viewHolderHead.headImage);
                viewHolderHead.baseText.setText(editBean.getEditText());
                app = (UserNameApplication) getContext().getApplicationContext();
                viewHolderHead.titleText.setText("by "+app.getUserName());
                break;
            case 1:
                if(editBean.getImagePath().equals(""))
                {
                    viewHolderBase.baseImage.setVisibility(View.GONE);
                }else {
                    Glide.with(getContext()).load(editBean.getImagePath()).into(viewHolderBase.baseImage);
                }
                viewHolderBase.baseText.setText(editBean.getEditText());
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
        public static final int TYPE_BASE = 1;
        ImageView baseImage;
        TextView baseText;
    }



    @Override
    public int getItemViewType(int position){
        if(position == 0) {
            return 0;
        }
        else {
            return 1;
        }
    }


    public class ItemType {
        public static final int ITEM_TYPE_MAX_COUNT = 2;
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
