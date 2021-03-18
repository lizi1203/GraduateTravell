package com.example.graduatetravell.Relax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.Mine.MineAdapter;
import com.example.graduatetravell.Mine.MineListItemModal;
import com.example.graduatetravell.R;

import java.util.List;

public class RelaxDetailAdapter extends ArrayAdapter<RelaxDetailHeadItemModal> {

    private int resourceId;
    private LayoutInflater inflater;

    public RelaxDetailAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelaxDetailHeadItemModal relaxDetailHeadItemModal = getItem(position);//获取当前项的实例
        View viewItem1 = null;
        View viewItem2 = null;
        View viewItem3 = null;

        RelaxDetailAdapter.ViewHolderHead viewHolderHead = null;
        RelaxDetailAdapter.ViewHolderBase viewHolderBase = null;
        RelaxDetailAdapter.ViewHolderComment viewHolderComment = null;
        int viewType =getItemViewType(position);

        if(convertView == null){
            //根据不同type设置不同布局
            switch(viewType){
                case 0:
                    viewHolderHead = new RelaxDetailAdapter.ViewHolderHead();
                    viewItem1 = inflater.inflate(R.layout.relax_detail_head,parent,false);
                    viewHolderHead.baseImage = viewItem1.findViewById(R.id.relax_detail_back_imageView);
                    viewHolderHead.headImage = viewItem1.findViewById(R.id.relax_detail_head_ImageView);
                    viewHolderHead.titleText = viewItem1.findViewById(R.id.relax_detail_title_textView);
                    viewHolderHead.baseText = viewItem1.findViewById(R.id.relax_detail_breif_textView);
                    viewItem1.setTag(viewHolderHead);//保存
                    convertView = viewItem1;
                    break;
                case 1:
                    viewHolderBase = new RelaxDetailAdapter.ViewHolderBase();
                    viewItem2 = inflater.inflate(R.layout.relax_detail_base, parent,false);
                    viewHolderBase.baseImage = viewItem2.findViewById(R.id.relax_detail_base_imageView);
                    viewHolderBase.baseText = viewItem2.findViewById(R.id.relax_detail_base_textView);
                    viewItem2.setTag(viewHolderBase);//保存
                    convertView = viewItem2;
                    break;
                case 2:
                    viewHolderComment= new RelaxDetailAdapter.ViewHolderComment();
                    viewItem3 = inflater.inflate(R.layout.relax_detail_comment, parent,false);
                    viewHolderComment.commentImage = viewItem3.findViewById(R.id.relax_detail_comment_head);
                    viewHolderComment.commentText = viewItem3.findViewById(R.id.relax_detail_comment_name);
                    viewHolderComment.dateText = viewItem3.findViewById(R.id.relax_detail_comment_date);
                    viewItem3.setTag(viewHolderComment);//保存
                    convertView = viewItem3;
                    break;
            }

        }
        else{
            //根据不同给type设置不同holder
            switch (viewType){
                case 0:
                    viewHolderHead = ( RelaxDetailAdapter.ViewHolderHead) convertView.getTag();//取出
                    break;
                case 1:
                    viewHolderBase = ( RelaxDetailAdapter.ViewHolderBase) convertView.getTag();//取出
                    break;
                case 2:
                    viewHolderComment = ( RelaxDetailAdapter.ViewHolderComment) convertView.getTag();//取出
            }

        }

        //根据不同type添加不同数据
        switch (viewType){
            case 0:
                Glide.with(getContext()).load(relaxDetailHeadItemModal.getBackImage()).into(viewHolderHead.baseImage);
                Glide.with(getContext()).load(relaxDetailHeadItemModal.getImageUrl()).into(viewHolderHead.headImage);
                viewHolderHead.titleText.setText(relaxDetailHeadItemModal.getTitleString());
                viewHolderHead.baseText.setText(relaxDetailHeadItemModal.getText());
                break;
            case 1:
                Glide.with(getContext()).load(relaxDetailHeadItemModal.getImageUrl()).into(viewHolderBase.baseImage);
                viewHolderBase.baseText.setText(relaxDetailHeadItemModal.getText());
                break;
            case 2:
                Glide.with(getContext()).load(relaxDetailHeadItemModal.getImageUrl()).into(viewHolderComment.commentImage);
                viewHolderComment.commentText.setText(relaxDetailHeadItemModal.getText());
                viewHolderComment.dateText.setText(relaxDetailHeadItemModal.getTitleString());
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

    private class ViewHolderComment{
        public static final int TYPE_COMMENT = 2;
        ImageView commentImage;
        TextView commentText;
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
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}
