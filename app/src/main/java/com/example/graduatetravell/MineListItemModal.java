package com.example.graduatetravell;

public class MineListItemModal {
    //item的名称
    private String itemName;
    //item的图标
    private int iconId;

    public MineListItemModal(String itemName,int iconId){
        this.itemName = itemName;
        this.iconId = iconId;
    }

    public String getItemName(){
        return itemName;
    }

    public int getIconId(){
        return iconId;
    }
}
