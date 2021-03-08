package com.example.graduatetravell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MineAdapter extends ArrayAdapter<MineListItemModal> {

        private int resourceId;
        private LayoutInflater inflater;

        public MineAdapter(Context context, int resource, List<MineListItemModal> objects) {
        super(context, resource, objects);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MineListItemModal mineListItemModal = getItem(position);//获取当前项的实例
            View viewItem1 = null;
            View viewItem2 = null;

            ViewHolderHead viewHolderHead = null;
            ViewHolderBase viewHolderBase = null;
            int viewType =getItemViewType(position);

            if(convertView == null){
                //根据不同type设置不同布局
                switch(viewType){
                    case 0:
                        viewHolderHead = new ViewHolderHead();
                        viewItem1 = inflater.inflate(R.layout.fragment_mine_head_item,parent,false);
                        viewHolderHead.itemName = viewItem1.findViewById(R.id.head_item_name);
                        viewHolderHead.icon = viewItem1.findViewById(R.id.head_item_image);
                        viewItem1.setTag(viewHolderHead);//保存
                        convertView = viewItem1;
                        break;
                    case 1:
                        viewHolderBase = new ViewHolderBase();
                        viewItem2 = inflater.inflate(R.layout.fragment_mine_base_item, parent,false);
                        viewHolderBase.itemName = viewItem2.findViewById(R.id.base_item_name);
                        viewHolderBase.icon = viewItem2.findViewById(R.id.base_item_image);
                        viewItem2.setTag(viewHolderBase);//保存
                        convertView = viewItem2;
                        break;
                }

            }
            else{
                //根据不同给type设置不同holder
                switch (viewType){
                    case 0:
                        viewHolderHead = (ViewHolderHead) convertView.getTag();//取出
                        break;
                    case 1:
                        viewHolderBase = (ViewHolderBase) convertView.getTag();//取出
                }
                
            }
            
            //根据不同type添加不同数据
            switch (viewType){
                case 0:
                    viewHolderHead.icon.setImageResource(mineListItemModal.getIconId());
                    viewHolderHead.itemName.setText(mineListItemModal.getItemName());
                    break;
                case 1:
                    viewHolderBase.icon.setImageResource(mineListItemModal.getIconId());
                    viewHolderBase.itemName.setText(mineListItemModal.getItemName());
                    break;
            }
            return convertView;
        }

        private class ViewHolderHead{
            public static final int  TYPE_HEAD = 0;
            ImageView icon;
            TextView itemName;
        }

         private class ViewHolderBase{
            public static final int TYPE_BASE = 1;
            ImageView icon;
            TextView itemName;
        }

        @Override
        public int getItemViewType(int position){
            return this.getItem(position).getItem_type();
        }


        public class ItemType {
            public static final int ITEM_TYPE_MAX_COUNT = 2;
        }

        public int getViewTypeCount() {
            return ItemType.ITEM_TYPE_MAX_COUNT;
        }

        //设置HEADITEM不可点击
        @Override
        public boolean areAllItemsEnabled() {
            // TODO Auto-generated method stub
            return false;
        }

    @Override
        public boolean isEnabled(int position) {
            if (position == 0  ) {
                return false;
            }
            return true;

        }


    }

