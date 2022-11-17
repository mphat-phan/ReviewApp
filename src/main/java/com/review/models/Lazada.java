package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Lazada {
    private String url ;
    public List<Product> getProductsByQueryLazada(String q) throws IOException,RuntimeException {
        url = "https://www.lazada.vn/catalog/?_keyori=ss&ajax=true&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+q).method(Connection.Method.GET).cookie("x5sec","7b22617365727665722d6c617a6164613b32223a226163646331643964383766393931356232396666643239343663626632333564434a4868305a7347454f53366a4e5777395a32514b7a434b6d5a6e332b2f2f2f2f2f384251414d3d227d").ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("mods").getJSONArray("listItems");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(jsonArray.getJSONObject(i).getInt("itemId"));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("Price"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("originalPrice"));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public List<Rate> getRatesByQueryLazada(Integer id) throws IOException,RuntimeException {
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
        Lazada lazada = new Lazada();
        List<Product> productList = new ArrayList<>();
        productList = lazada.getProductsByQueryLazada("iphone");
//        List<Rate> productListReviews = new ArrayList<>();
//        productListReviews = tiki.getRatesByQuery(184061913);
        System.out.println("Hello");
    }
}