package com.review.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tiki {
    private String url = "https://tiki.vn/api/v2/products?limit=10&q=";
    public List<Product> getProductsByQuery(String q) throws IOException,RuntimeException {

        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+q).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("thumbnail_url"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                product.setPrice_sale(20000);
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public static void main(String[] args) throws IOException {
        Tiki tiki = new Tiki();
        List<Product> productList = new ArrayList<>();
        productList = tiki.getProductsByQuery("iphone");
        System.out.println("Hello");
    }
}