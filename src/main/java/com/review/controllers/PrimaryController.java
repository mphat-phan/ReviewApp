package com.review.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import com.review.models.Client;
import com.review.models.Product;
import com.review.models.ProductDetail;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    private Client client ;
    private Product Tiki;
    private Product Shopee;
    private Product Sendo;
    private Product Lazada;

    @FXML
    private BorderPane container;
    private ItemListController itemListController;
    private ItemDetailController itemDetailController;
    private InfoDetailController infoDetailController;
    private RatingAggregatorController ratingAggregatorController;
    @FXML
    private Label rating_aggregator_button;

    @FXML
    private Label search_product_button;
    @FXML
    private TextField search_product;

    public void setInfoDetailController(InfoDetailController infoDetailController) {
        this.infoDetailController = infoDetailController;
    }

    public InfoDetailController getInfoDetailController() {
        return infoDetailController;
    }
    public ItemDetailController getItemDetailController() {
        return itemDetailController;
    }

    public void setItemDetailController(ItemDetailController itemDetailController) {
        this.itemDetailController = itemDetailController;
    }

    @FXML
    void search_enter(ActionEvent event)throws IOException,ClassNotFoundException {
        client.SearchProduct(search_product.getText());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
        fxmlLoader.load();
        itemListController = fxmlLoader.getController();
        itemListController.productList = client.ReceiveList();
        swapItemList();
    }

    @FXML
    void rating_aggregator_press(MouseEvent event) throws IOException, ClassNotFoundException {
        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        //client.GetReviewProduct(184061913);
        //ratingAggregatorController.rateList = client.ReceiveListReviews();

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
        try {

            ratingAggregatorController.openRatingAggregator(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void swapItemDetail(Product product, ProductDetail productDetail){
        try{


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_detail.fxml"));
            fxmlLoader.load();
            itemDetailController = fxmlLoader.getController();
            itemDetailController.openItemDetail(this);
            itemDetailController.product_name_label.setText(product.getProductName());
            itemDetailController.product_price_label.setText("VNĐ"+product.getPrice());
            itemDetailController.product_sale_price_label.setText("VNĐ"+product.getPrice_sale());
            String[] images ;
            images = productDetail.getImagesUrl();
                if(images.length>=3) {
                    Image image = new Image(product.getImageUrl());
                    Image image2 = new Image(images[1]);
                    Image image3 = new Image(images[2]);
                    itemDetailController.product_image_1.setImage(image);
                    itemDetailController.product_image_2.setImage(image2);
                    itemDetailController.product_image_3.setImage(image3);
                }
                else {
                    Image image = new Image(product.getImageUrl());
                    itemDetailController.product_image_1.setImage(image);
                }
        }catch (IOException e){

        }
    }
    public void disconnect() throws IOException, ClassNotFoundException {
        client.disconnect();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                client=new Client();
                client.ConnectClient();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
                fxmlLoader.load();
                itemListController = fxmlLoader.getController();

                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
                fxmlLoader.load();
                ratingAggregatorController = fxmlLoader.getController();

                client.SearchProduct("ipad");
                itemListController.productList = client.ReceiveList();

//                client.GetReviewProduct(184061913);
//                ratingAggregatorController.rateList = client.ReceiveListReviews();

                swapRatingAggregator();
                swapItemList();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
    }
    public void SetClient(Client cl){
        this.client=client;
    }
    public Client getClient(){
        return client;
    }

}
