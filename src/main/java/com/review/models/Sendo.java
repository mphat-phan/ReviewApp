package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Sendo {
    private String url ;
    public List<Product> getProductsByQuery(String q) throws IOException,RuntimeException {
        url = "https://searchlist-api.sendo.vn/web/products?page=1&size=60&sortType=rank&q=";
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
                product.setPrice(String.valueOf(jsonArray.getJSONObject(i).getInt("default_price_max")));
                product.setPrice_sale(String.valueOf(jsonArray.getJSONObject(i).getInt("sale_price_max")));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public List<Rate> getRatesByQuery(Integer id) throws IOException,RuntimeException {
        url = "https://tiki.vn/api/v2/reviews?product_id=";
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url+id).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setDate(jsonArray.getJSONObject(i).getJSONObject("created_by").getString("created_time"));
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating"));
                rate.setUsername(jsonArray.getJSONObject(i).getJSONObject("created_by").getString("full_name"));
                rate.setUserImageUrl(jsonArray.getJSONObject(i).getJSONObject("created_by").getString("avatar_url"));
                rate.setComment(jsonArray.getJSONObject(i).getString("content"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Sendo sendo = new Sendo();
        List<Product> productList = new ArrayList<>();
        productList = sendo.getProductsByQuery("iphone");
//        List<Rate> productListReviews = new ArrayList<>();
//        productListReviews = tiki.getRatesByQuery(184061913);
        System.out.println("Hello");
    }
}