package com.review.controllers;

import com.review.MyListener;
import com.review.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemListController implements Initializable {
    @FXML
    private ScrollPane product_list_scroll;
    @FXML
    private GridPane product_list_grid;
    public List<Product> productList = new ArrayList<>();
    private MyListener myListener;

    private PrimaryController primaryController;
    public List<Product> getData() {
        List<Product> products = new ArrayList<>();
        Product product;

        product = new Product();
        product.setProductName("Iphone 1");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://cdn.tgdd.vn/Products/Images/42/251192/iphone-14-pro-max-tim-thumb-600x600.jpg");
        products.add(product);

        product = new Product();
        product.setProductName("[11.11_SALE LỚN NHẤT NĂM ] iPhone 14 - Hàng Chính Hãng VN/A - Hàng Có Sẵn Giao Nhanh Trong Ngày");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://lzd-img-global.slatic.net/g/p/8d96d9407c232c862422a4ed255c05ed.png");
        products.add(product);

        product = new Product();
        product.setProductName("Sản phẩm 3");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://vn-live-01.slatic.net/p/b72b0a0bdddf15b29419975efd47d0f6.png");
        products.add(product);

        product = new Product();
        product.setProductName("Sản phẩm 4");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://cdn.tgdd.vn/Products/Images/42/251192/iphone-14-pro-max-tim-thumb-600x600.jpg");
        products.add(product);

        product = new Product();
        product.setProductName("Sản phẩm 5");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://cdn.tgdd.vn/Products/Images/42/251192/iphone-14-pro-max-tim-thumb-600x600.jpg");
        products.add(product);

        product = new Product();
        product.setProductName("Sản phẩm 6");
        product.setPrice(30000);
        product.setPrice_sale(20000);
        product.setImageUrl("https://cdn.tgdd.vn/Products/Images/42/251192/iphone-14-pro-max-tim-thumb-600x600.jpg");
        products.add(product);

        return products;
    }

    public void openItemList(PrimaryController primaryController1){
        this.primaryController = primaryController1;
        //productList.addAll(getData());

        if(productList.size()>0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    primaryController.swapItemDetail();
                }
            };
        }

        int col = 0;
        int row = 1;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
            Pane newPane = fxmlLoader.load();
            ItemListController itemListController = fxmlLoader.getController();
            primaryController.setContainer(newPane);

            for (int i = 0; i < productList.size(); i++){
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(productList.get(i), myListener);

                if(col == 5){
                    col = 0;
                    row++;
                }

                itemListController.product_list_grid.add(anchorPane, col++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                itemListController.product_list_scroll.setPadding(new Insets(0, 0, 0, 30));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
