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
    //详情页数据的序列号
    private String detailID;


    public StoryRecyclerItemModal() {

    }

    public StoryRecyclerItemModal(String itemTitle, String iconURL, String itemAuthor, String itemHeadURL, String detailID) {
        this.itemTitle = itemTitle;
        this.iconURL = iconURL;
        this.itemAuthor = itemAuthor;
        this.itemHeadURL = itemHeadURL;
        this.detailID = detailID;
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

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }
}
