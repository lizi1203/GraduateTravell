package com.example.graduatetravell.Relax;

public class RelaxDetailHeadItemModal extends RelaxDetailBaseItemModal{
    private String titleString;
    private String backImage;

    public RelaxDetailHeadItemModal(String text, String imageUrl, int item_type, String titleString, String backImage) {
        super(text, imageUrl, item_type);
        this.titleString = titleString;
        this.backImage = backImage;
    }

    public RelaxDetailHeadItemModal() {
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }
}
