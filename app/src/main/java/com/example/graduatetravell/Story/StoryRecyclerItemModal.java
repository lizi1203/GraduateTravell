package com.example.graduatetravell.Story;

public class StoryRecyclerItemModal {

    //item的标题
    private String itemTitle;
    //item的图标
    private String iconURL;
    //item的作者名称
    private String itemAuthor;
    //item的头像
    private String itemHeadURL;

    public StoryRecyclerItemModal(String itemTitle, String iconURL, String itemAuthor, String itemHead) {
        this.itemTitle = itemTitle;
        this.iconURL = iconURL;
        this.itemAuthor = itemAuthor;
        this.itemHeadURL = itemHead;
    }

    public StoryRecyclerItemModal() {

    }


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getItemAuthor() {
        return itemAuthor;
    }

    public void setItemAuthor(String itemAuthor) {
        this.itemAuthor = itemAuthor;
    }

    public String getItemHeadURL() {
        return itemHeadURL;
    }

    public void setItemHeadURL(String itemHead) {
        this.itemHeadURL = itemHead;
    }
}
