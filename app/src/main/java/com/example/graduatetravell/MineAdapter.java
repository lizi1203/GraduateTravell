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
        public MineAdapter(Context context, int resource, List<MineListItemModal> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MineListItemModal mineListItemModal = getItem(position);//获取当前项的实例
            View view;
            ViewHolder viewHolder;

            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemName = view.findViewById(R.id.base_item_name);
                viewHolder.icon = view.findViewById(R.id.base_item_image);
                view.setTag(viewHolder);//保存
            }
            else{
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();//取出
            }

            viewHolder.icon.setImageResource(mineListItemModal.getIconId());
            viewHolder.itemName.setText(mineListItemModal.getItemName());
            return view;
        }

        private class ViewHolder{
            ImageView icon;
            TextView itemName;
        }

    }

