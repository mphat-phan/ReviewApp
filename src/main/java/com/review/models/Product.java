package com.review.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private List<Rate> rates;
    private Integer productID;
    private String productName;
    private int price;
    private Integer price_sale;
    private String imageUrl;

    private int idshop;

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(int price_sale) {
        this.price_sale = price_sale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
