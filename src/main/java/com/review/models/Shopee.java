package com.review.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Shopee {
    private String url ;
    public List<Product> getProductsByQueryShopee(String q) throws IOException,RuntimeException {
        url = "https://shopee.vn/api/v4/search/search_items?by=relevancy&keyword="+q+"&limit=10&newest=0&order=desc";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .header("af-ac-enc-dat", "hello")
                .ignoreContentType(true)
                .execute();
        Document doc = res.parse();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(jsonArray.getJSONObject(i).getJSONObject("item_basic").getInt("itemid"));
                product.setIdshop(jsonArray.getJSONObject(i).getJSONObject("item_basic").getInt("shopid"));
                product.setProductName(jsonArray.getJSONObject(i).getJSONObject("item_basic").getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getJSONObject("item_basic").getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getJSONObject("item_basic").getInt("price_max_before_discount"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getJSONObject("item_basic").getInt("price"));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public String[] change(String s){
        return s.replaceAll("[^0-9a-zA-Z,]","").split(",");
    }
    public ProductDetail getDetailProduct(String itemid,String shopid) throws IOException,RuntimeException {
        ProductDetail ProductDetail;
        url = "https://shopee.vn/api/v4/item/get?itemid="+itemid+"&shopid="+shopid;
        Connection.Response res = Jsoup.connect(url).header("af-ac-enc-dat", "hello").method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text()).getJSONObject("data");
        JSONArray jsonArray= null;
        try {
            ProductDetail = new ProductDetail();
            jsonArray = json.getJSONArray("tier_variations");
            ProductDetail.setRating_average(json.getJSONObject("item_rating").getInt("rating_star"));
            String[] n=change(json.getJSONObject("item_rating").get("rating_count").toString());
            ProductDetail.setReview_count(Integer.parseInt(n[0]));
            ProductDetail.setDescription(json.getString("description"));
            n=change(jsonArray.getJSONObject(0).get("images").toString());
            for(int i=0;i<n.length;i++)
                n[i]="https://cf.shopee.vn/file/"+n[i];
            ProductDetail.setImagesUrl(n);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuery(String itemid,String shopid) throws IOException,RuntimeException {
        url = "https://shopee.vn/api/v2/item/get_ratings?flag=1&itemid="+itemid+"&limit=10&offset=0&shopid="+shopid;
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).header("af-ac-enc-dat", "hello").method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("data").getJSONArray("ratings");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating_star"));
                rate.setUsername(jsonArray.getJSONObject(i).getString("author_username"));
                rate.setUserImageUrl("https://cf.shopee.vn/file/"+jsonArray.getJSONObject(i).getString("author_portrait"));
                rate.setComment(jsonArray.getJSONObject(i).getString("comment"));
                String[] n=change(jsonArray.getJSONObject(i).get("images").toString());
                List<String> c=new ArrayList<>();
                for(int j=0;j<n.length;j++) {
                    c.add("https://cf.shopee.vn/file/"+n[j]);
                }
                rate.setImageUrl(c);
                ReviewList.add(rate);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Shopee shopee = new Shopee();
        //List<Product> list=shopee.getProductsByQueryShopee("iphone");;
//          List<Product> productList = new ArrayList<>();
//        productList = tiki.getProductsByQuery("iphone");
//        List<Rate> productListReviews = new ArrayList<>();
//        productListReviews = tiki.getRatesByQuery(184061913);
        List<Rate>list=shopee.getRatesByQuery("5600084939","88201679");
        System.out.println(list.get(1).getComment());
    }
}