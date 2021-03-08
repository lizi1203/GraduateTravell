package com.example.graduatetravell;

public class MineListItemModal {
    //item的名称
    private String itemName;
    //item的图标
    private int iconId;
    //item类型
    private int item_type = 0;

    public MineListItemModal(String itemName,int iconId,int item_type){
        this.itemName = itemName;
        this.iconId = iconId;
        this.item_type = item_type;
    }

    public String getItemName(){
        return itemName;
    }

    public int getIconId(){
        return iconId;
    }

    public int getItem_type(){ return item_type; };
}
