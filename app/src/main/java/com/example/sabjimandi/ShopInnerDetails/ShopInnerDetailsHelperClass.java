package com.example.sabjimandi.ShopInnerDetails;

public class ShopInnerDetailsHelperClass {

    String itemName;
    String itemAmount;
    String itemImage;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public ShopInnerDetailsHelperClass(String itemName, String itemAmount, String itemImage) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemImage = itemImage;
    }

    public ShopInnerDetailsHelperClass(){


    }

}
