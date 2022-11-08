package com.review.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

public class PrimaryController implements Initializable {
    @FXML
    private BorderPane container;
    private ItemListController itemListController;
    private ItemDetailController itemDetailController;

    public void setContainer(Pane newPane){
        container.setCenter(newPane);
    }

    public void swapItemList(){
        itemListController.openItemList(this);
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
            swapItemList();
        }catch (IOException e){

        }


    }
}
