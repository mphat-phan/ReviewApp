package com.review.models;

import java.io.Serializable;

public class Product implements Serializable {
    private  int productID;
    private String productName;
    private int price;
    private int price_sale;
    private String imageUrl;
    public Integer getproductID() {
        return productID;
    }

    public void setproductID(Integer productID) {
        this.productID = productID;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(Integer price_sale) {
        this.price_sale = price_sale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
