package com.example.graduatetravell.Story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.graduatetravell.R;

import java.util.List;

import static com.baidu.vi.VIContext.getContext;

public class EditAdapter extends BaseAdapter {

    private List<EditBean> mData;
    private Context mContext;
    private SubClickListener subClickListener;
    private Bitmap bm;

    public EditAdapter(Context mContext, List<EditBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View titleView = null;
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.edit_base_layout, null);
            holder = new ViewHolder(convertView);
            if(position == 0){
                holder.editText.setHint("请输入标题");
                holder.addButton.setHint("请选择封面图片");
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final EditBean itemObj = mData.get(position);

        //This is important. Remove TextWatcher first.
        if (holder.editText.getTag() instanceof TextWatcher) {
            holder.editText.removeTextChangedListener((TextWatcher) holder.editText.getTag());
        }

        holder.editText.setText(itemObj.getEditText());
        bm = BitmapFactory.decodeFile(itemObj.getImagePath());
        holder.imageView.setImageBitmap(bm);
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.setEditText("");
                } else {
                    itemObj.setEditText(s.toString());
                }
            }
        };

        holder.editText.addTextChangedListener(watcher);
        holder.editText.setTag(watcher);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImag = new Intent();
                intentImag = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentImag.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                if (subClickListener != null) {
                    subClickListener.OntopicClickListener(v, mData.get(position), position);
                }

                ((Activity) mContext).startActivityForResult(intentImag,1);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private EditText editText;
        private ImageView imageView;
        private Button addButton;

        public ViewHolder(View convertView) {
            editText = (EditText) convertView.findViewById(R.id.title_editText);
            imageView = (ImageView) convertView.findViewById(R.id.add_imageView);
            addButton = (Button)convertView.findViewById(R.id.addImage_button);

        }
    }


    //回调方法，当adapter中的部件被点击时，可以传递当前position到activity中进行操作
    public void setsubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }

    public interface SubClickListener {
        void OntopicClickListener(View v, EditBean bean, int position);
    }

}
