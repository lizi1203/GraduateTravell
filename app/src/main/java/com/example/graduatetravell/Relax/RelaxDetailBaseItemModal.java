package com.example.graduatetravell.Relax;

public class RelaxDetailBaseItemModal {

    //文本
    private String text;
    //图标
    private String  imageUrl;
    //item类型
    private int item_type = 1;


    public RelaxDetailBaseItemModal(String text, String imageUrl, int item_type) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.item_type = item_type;
    }

    public RelaxDetailBaseItemModal() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }
}
