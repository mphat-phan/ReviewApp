package com.review.controllers;

import com.review.models.Rate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

public class RatingController {
    @FXML
    private Text comment_label;

    @FXML
    private Label date_label;

    @FXML
    private ImageView image_label;

    @FXML
    private Rating rating_label;

    @FXML
    private ImageView user_image_label;

    @FXML
    private Label user_name_label;


    private String dirPath = new java.io.File("images/an-danh.png").getAbsolutePath();
    public void setData(Rate rate){
        Image user_image = new Image(rate.getUserImageUrl());
        if(!rate.getImageUrl().isEmpty())
        {
            Image image = new Image(rate.getImageUrl().get(0));
            image_label.setImage(image);
        }
        user_name_label.setText(rate.getUsername());
        user_image_label.setImage(user_image);
        comment_label.setText(rate.getComment());
        date_label.setText(rate.getDate());
        rating_label.setRating(rate.getRating());
    }

}
