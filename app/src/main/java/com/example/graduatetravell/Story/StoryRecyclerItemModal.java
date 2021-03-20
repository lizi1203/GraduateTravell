package com.example.graduatetravell.Story;

import java.io.Serializable;

public class StoryRecyclerItemModal implements Serializable {

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

    //重写equals方法
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StoryRecyclerItemModal other = (StoryRecyclerItemModal) obj;
        if (!itemTitle.equals( other.itemTitle))
            return false;
        if (!iconURL.equals( other.iconURL))
            return false;
        if (!itemAuthor.equals( other.itemAuthor))
            return false;
        if (!itemHeadURL.equals( other.itemHeadURL))
            return false;
        if (!detailID.equals( other.detailID))
            return false;

        return true;
    }
}
