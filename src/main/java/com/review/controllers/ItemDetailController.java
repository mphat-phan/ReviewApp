package com.review.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDetailController implements Initializable{
    @FXML
    private HBox rating_list;
    private static PrimaryController primaryController;
    @FXML
    void returnItemList(MouseEvent event) {
        primaryController.swapItemList();
    }
    public void openItemDetail(PrimaryController primaryController1){
        primaryController = primaryController1;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_detail.fxml"));
            Pane newPane = fxmlLoader.load();
            ItemDetailController itemDetailController = fxmlLoader.getController();
            primaryController.setContainer(newPane);

            newPane = FXMLLoader.load(getClass().getResource("/com/review/rating_list.fxml"));
            itemDetailController.rating_list.getChildren().addAll(newPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
