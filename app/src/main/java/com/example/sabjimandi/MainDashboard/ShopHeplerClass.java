package com.example.sabjimandi.MainDashboard;

public class ShopHeplerClass {

    String shopImage;
    String shopName;
    String shopGenre;
    String shopDistanceTime;
    String shopRating;

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDistanceTime() {
        return shopDistanceTime;
    }

    public String getShopGenre() {
        return shopGenre;
    }

    public void setShopGenre(String shopGenre) {
        this.shopGenre = shopGenre;
    }

    public void setShopDistanceTime(String shopDistanceTime) {
        this.shopDistanceTime = shopDistanceTime;
    }

    public String getShopRating() {
        return shopRating;
    }

    public void setShopRating(String shopRating) {
        this.shopRating = shopRating;
    }

    public ShopHeplerClass(String shopImage, String shopName,String shopGenre, String shopDistanceTime, String shopRating) {
        this.shopImage = shopImage;
        this.shopName = shopName;
        this.shopGenre = shopGenre;
        this.shopDistanceTime = shopDistanceTime;
        this.shopRating = shopRating;
    }

    public  ShopHeplerClass(){


    }

}
