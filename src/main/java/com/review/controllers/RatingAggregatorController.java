package com.review.controllers;

import com.review.MyListener;
import com.review.models.Client;
import com.review.models.Product;
import com.review.models.Rate;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RatingAggregatorController implements Initializable {

    @FXML
    private AnchorPane amazon_button;
    @FXML
    private AnchorPane ebay_button;
    @FXML
    private AnchorPane lazada_button;
    @FXML
    private AnchorPane sendo_button;
    @FXML
    private AnchorPane tiki_button;
    @FXML
    private ScrollPane rating_scroll;
    @FXML
    private GridPane rating_grid;
    @FXML
    private HBox pagination_list;
    public List<Rate> rateList = new ArrayList<>();
    private Pane pane;
    private int stepPagination = 0;
    private int pageNumDefault = 5;
    private PrimaryController primaryController;
    @FXML
    void amazon_button_presss(MouseEvent event) {
        this.ebay_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.amazon_button.getStyleClass().add("action");
    }

    @FXML
    void ebay_button_press(MouseEvent event) {
        this.amazon_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.ebay_button.getStyleClass().add("action");
    }

    @FXML
    void lazada_button_press(MouseEvent event) {
        this.amazon_button.getStyleClass().remove("action");
        this.ebay_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().add("action");
    }

    @FXML
    void sendo_button_press(MouseEvent event) {
        this.amazon_button.getStyleClass().remove("action");
        this.ebay_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().add("action");
    }

    @FXML
    void tiki_button_press(MouseEvent event) {
        this.amazon_button.getStyleClass().remove("action");
        this.ebay_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().add("action");
    }
    @FXML
    void pagination_left_press(MouseEvent event) {
        if(this.stepPagination != 0){
            setStepPagination(this.stepPagination-1);
            setPagination();
        }
    }

    @FXML
    void pagination_right_press(MouseEvent event) {
        setStepPagination(this.stepPagination+1);
        setPagination();
    }
    public void setStepPagination(int stepPagination) {
        this.stepPagination = stepPagination;
    }
    private EventHandler clickPagination = new EventHandler() {

        @Override
        public void handle(Event event) {
            Node node;
            for(int i = 0; i < pageNumDefault; i++){
                node = pagination_list.getChildren().get(i);
                node.getStyleClass().remove("button-pagination-action");
            }
            ((Button)event.getSource()).getStyleClass().add("button-pagination-action");
        }
    };

    public void setPagination(){
        pagination_list.getChildren().clear();
        Button button;
        int startPage = (pageNumDefault * stepPagination) + 1;
        for(int i = 0; i < pageNumDefault; i++){
            button = new Button();
            button.getStyleClass().add("button-pagination");
            button.addEventHandler(MouseEvent.MOUSE_PRESSED, clickPagination);
            button.setText(String.valueOf(startPage++));
            pagination_list.getChildren().add(button);
        }
    }
    public void openRatingAggregator(PrimaryController primaryController1)throws IOException,ClassNotFoundException{
        if(pane != null){
            primaryController.setContainer(pane);
        }
        else{
            this.primaryController = primaryController1;
            int col = 0;
            int row = 1;
            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
                pane = fxmlLoader.load();
                RatingAggregatorController ratingAggregatorController = fxmlLoader.getController();
                primaryController.setContainer(pane);

                /*--------------------View Rating List-------------*/
                for (int i = 0; i < rateList.size(); i++){
                    fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/review/rating.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    RatingController ratingController = fxmlLoader.getController();
                    ratingController.setData(rateList.get(i));
                    if(col == 2){
                        col = 0;
                        row++;
                    }
                    ratingAggregatorController.rating_grid.add(anchorPane, col++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));
                    ratingAggregatorController.rating_scroll.setPadding(new Insets(0, 0, 0, 0));

                    //set grid height
                    ratingAggregatorController.rating_grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    ratingAggregatorController.rating_grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    ratingAggregatorController.rating_grid.setMaxHeight(Region.USE_PREF_SIZE);
                    ratingAggregatorController.rating_grid.setAlignment(Pos.TOP_CENTER);

                    ratingAggregatorController.pagination_list.getChildren().clear();
                    setPagination();


                }

                /*---------------Set Pagination-------------------*/
                ratingAggregatorController.pagination_list.getChildren().clear();
                Button button;
                int startPage = (pageNumDefault * stepPagination) + 1;
                for(int i = 0; i < pageNumDefault; i++){
                    button = new Button();
                    button.getStyleClass().add("button-pagination");
                    button.addEventHandler(MouseEvent.MOUSE_PRESSED, event ->{
                        Node node;
                        for(int j = 0; j < pageNumDefault; j++){
                            node = ratingAggregatorController.pagination_list.getChildren().get(j);
                            node.getStyleClass().remove("button-pagination-action");
                        }

                        ((Button)event.getSource()).getStyleClass().add("button-pagination-action");
                    });
                    button.setText(String.valueOf(startPage++));
                    ratingAggregatorController.pagination_list.getChildren().add(button);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
