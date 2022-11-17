package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Sendo {
    private String url ;
    public List<Product> getProductsByQuerySendo(String q) throws IOException,RuntimeException {
        url = "https://searchlist-api.sendo.vn/web/products?page=1&size=10&sortType=rank&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+q).method(Connection.Method.GET).header("referer","https://www.sendo.vn/").ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(jsonArray.getJSONObject(i).getInt("id"));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("default_price_max"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("sale_price_max"));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public ProductDetail getDetailProductSendo(Integer ID) throws IOException,RuntimeException {
        ProductDetail ProductDetail;
        url = "https://api.tiki.vn/product-detail/api/v1/products/";
        Connection.Response res = Jsoup.connect(url+ID).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text());
        JSONArray jsonArray= null;
        try {
            ProductDetail = new ProductDetail();
            jsonArray = new JSONObject(doc.text()).getJSONArray("images");
            ProductDetail.setRating_average(json.getInt("rating_average"));
            ProductDetail.setReview_count(json.getInt("review_count"));
            ProductDetail.setDescription(json.getString("description"));
            String[] a = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                a[i] = jsonArray.getJSONObject(i).getString("base_url");
            }
            ProductDetail.setImagesUrl(a);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuerySendo(Integer id) throws IOException,RuntimeException {
        url = "https://ratingapi.sendo.vn/product/"+id+"/rating?limit=5&star=all";
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setDate("");
                rate.setRating(jsonArray.getJSONObject(i).getInt("star"));
                rate.setUsername(jsonArray.getJSONObject(i).getString("user_name"));
                rate.setUserImageUrl(jsonArray.getJSONObject(i).getString("avatar"));
                rate.setComment(jsonArray.getJSONObject(i).getString("comment"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Sendo sendo = new Sendo();
//        List<Product> productList = new ArrayList<>();
//        productList = sendo.getProductsByQuery("iphone");
      List<Rate> productListReviews = new ArrayList<>();
      productListReviews = sendo.getRatesByQuerySendo(28188634);
        System.out.println("Hello");
    }
}