package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tiki {
    private String url ;
    public List<Product> getProductsByQuery(String q) throws IOException,RuntimeException {
        url = "https://tiki.vn/api/v2/products?limit=10&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+q).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(jsonArray.getJSONObject(i).getInt("id"));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("thumbnail_url"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("original_price"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("price"));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public ProductDetail getDetailProduct(Integer ID) throws IOException,RuntimeException {
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
        Tiki tiki = new Tiki();
        ProductDetail productDetail = new ProductDetail();
        productDetail = tiki.getDetailProduct(184061913);
//          List<Product> productList = new ArrayList<>();
//        productList = tiki.getProductsByQuery("iphone");
//        List<Rate> productListReviews = new ArrayList<>();
//        productListReviews = tiki.getRatesByQuery(184061913);
        System.out.println("Hello");
    }
}