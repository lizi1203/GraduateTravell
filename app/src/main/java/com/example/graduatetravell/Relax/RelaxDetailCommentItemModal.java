package com.example.graduatetravell.Relax;

public class RelaxDetailCommentItemModal extends RelaxDetailBaseItemModal{
    private String dataString;

    public RelaxDetailCommentItemModal(String text, String imageUrl, int item_type, String dataString) {
        super(text, imageUrl, item_type);
        this.dataString = dataString;
        setItem_type(2);
    }

    public RelaxDetailCommentItemModal() {
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}
