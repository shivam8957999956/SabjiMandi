package com.example.sabjimandi.MainDashboard.cartAttachments;

import com.example.sabjimandi.MainDashboard.Cart;

public class CartHelperClass {
    String cartImage;
    String cartName;
    String cartCost;
    String cartQuantity;

    public CartHelperClass(String cartImage, String cartName, String cartCost, String cartQuantity) {
        this.cartImage = cartImage;
        this.cartName = cartName;
        this.cartCost = cartCost;
        this.cartQuantity = cartQuantity;
    }

    public CartHelperClass(){

    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartCost() {
        return cartCost;
    }

    public void setCartCost(String cartCost) {
        this.cartCost = cartCost;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
