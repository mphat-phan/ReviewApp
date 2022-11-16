package com.review.models;

import java.io.Serializable;

public class Product implements Serializable {
    private  int productID;
    private String productName;
    private String price;
    private String price_sale;
    private String imageUrl;

    private Integer ratingAverage;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(String price_sale) {
        this.price_sale = price_sale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Integer ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

}
