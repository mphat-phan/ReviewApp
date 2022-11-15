package com.review.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.review.models.Client;
import com.review.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class PrimaryController implements Initializable {
    private static Client client ;
    private Product Tiki;
    private Product Shopee;
    private Product Sendo;
    private Product Lazada;

    @FXML
    private BorderPane container;
    private ItemListController itemListController;
    private ItemDetailController itemDetailController;
    private RatingAggregatorController ratingAggregatorController;
    @FXML
    private Label rating_aggregator_button;

    @FXML
    private Label search_product_button;
    @FXML
    private TextField search_product;
    @FXML
    void search_enter(ActionEvent event)throws IOException,ClassNotFoundException {
        client.SearchProduct(search_product.getText());
    }

    @FXML
    void rating_aggregator_press(MouseEvent event) {
        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");

        swapRatingAggregator();
        this.rating_aggregator_button.getStyleClass().add("action");
    }

    @FXML
    void search_product_press(MouseEvent event) {
        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");

        swapItemList();
        this.search_product_button.getStyleClass().add("action");
    }

    public void setContainer(Pane newPane){
        container.setCenter(newPane);
    }

    public void swapItemList(){
        itemListController.openItemList(this);
    }
    public void swapRatingAggregator(){
        ratingAggregatorController.openRatingAggregator(this);
    }
    public void swapItemDetail(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_detail.fxml"));
            fxmlLoader.load();
            itemDetailController = fxmlLoader.getController();
            itemDetailController.openItemDetail(this);
        }catch (IOException e){

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
                fxmlLoader.load();
                itemListController = fxmlLoader.getController();
                itemListController.productList.addAll(itemListController.getData());
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
                fxmlLoader.load();
                ratingAggregatorController = fxmlLoader.getController();

                ratingAggregatorController.rateList.addAll(ratingAggregatorController.getData());
                swapRatingAggregator();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public static void SetClient(Client cl){
        client=cl;
    }
    public static Client getClient(){
        return client;
    }
}
