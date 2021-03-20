package com.example.graduatetravell.Story;

public class StoryDetailItemModal {

    private String headImage;
    private String backImage;
    private String text;
    private String breifText;
    //item类型
    private int item_type = 1;

    public StoryDetailItemModal(String headImage, String backImage, String text, String breifText, int item_type) {
        this.headImage = headImage;
        this.backImage = backImage;
        this.text = text;
        this.breifText = breifText;
        this.item_type = item_type;
    }


    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getBreifText() {
        return breifText;
    }

    public void setBreifText(String breifText) {
        this.breifText = breifText;
    }
}
