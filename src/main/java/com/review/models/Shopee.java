package com.review.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shopee {
    private String url ;
    public void getProductsByQueryShopee(String q) throws IOException,RuntimeException {
        url = "https://shopee.vn/api/v4/search/search_items?by=relevancy&keyword="+q+"&limit=10&newest=0&order=desc&page_type=search&scenario=PAGE_GLOBAL_SEARCH&version=2";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .header("af-ac-enc-dat", "hello")
                .ignoreContentType(true)
                .execute();
        Document doc = res.parse();
        System.out.println(doc.text());
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                product = new Product();
//                product.setproductID(jsonArray.getJSONObject(i).getInt("id"));
//                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
//                product.setImageUrl(jsonArray.getJSONObject(i).getString("thumbnail_url"));
//                product.setPrice(jsonArray.getJSONObject(i).getInt("original_price"));
//                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("price"));
//                productList.add(product);
//            }
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        return productList;
    }
    public static void main(String[] args) throws IOException {
        Shopee shopee = new Shopee();
        shopee.getProductsByQueryShopee("iphone");
//          List<Product> productList = new ArrayList<>();
//        productList = tiki.getProductsByQuery("iphone");
//        List<Rate> productListReviews = new ArrayList<>();
//        productListReviews = tiki.getRatesByQuery(184061913);
        System.out.println("Hello");
    }
}
