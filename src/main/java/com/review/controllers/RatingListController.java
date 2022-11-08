package com.review.controllers;

import com.review.models.Rate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RatingListController implements Initializable {
    @FXML
    private GridPane rating_grid;

    @FXML
    private ScrollPane rating_scroll;

    private List<Rate> rateList = new ArrayList<>();
    private List<Rate> getData() {
        List<Rate> rates = new ArrayList<>();
        Rate rate;

        for (int i = 0; i < 10; i++){
            rate = new Rate();
            rate.setComment("Xin chao nhe");
            rate.setImageUrl("/images/product.png");
            rate.setDate("7/11/2022");
            rate.setUsername("Phan Minh Phat");
            rate.setUserImageUrl("/images/user.png");
            rates.add(rate);
        }
        return rates;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rateList.addAll(getData());

        int row = 1;
        try {
            for (int i = 0; i < rateList.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/rating.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                RatingController ratingController = fxmlLoader.getController();
                ratingController.setData(rateList.get(i));

                rating_grid.add(anchorPane, 0, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
                rating_scroll.setPadding(new Insets(0, 0, 0, 0));

                //set grid height
                rating_grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                rating_grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                rating_grid.setMaxHeight(Region.USE_PREF_SIZE);
                rating_grid.setAlignment(Pos.TOP_CENTER);



            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
