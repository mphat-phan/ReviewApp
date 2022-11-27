package com.review.controllers;

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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RatingAggregatorController implements Initializable {

    @FXML
    public AnchorPane shopee_button;
    public ImageView ratingAgg_image;
    public Label price_ratingAgg_label;
    public Label priceSale_ratingAgg_label;
    public Label rating_average;


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
    @FXML
    public Text productRating_name_label;

    public List<Rate> rateList = new ArrayList<>();

    public List<Rate> getTiki() {
        return Tiki;
    }

    public void setTiki(List<Rate> tiki) {
        Tiki = tiki;
    }

    public List<Rate> getSendo() {
        return Sendo;
    }

    public void setSendo(List<Rate> sendo) {
        Sendo = sendo;
    }

    public List<Rate> getShoppe() {
        return Shoppe;
    }

    public void setShoppe(List<Rate> shoppe) {
        Shoppe = shoppe;
    }

    public List<Rate> getLazada() {
        return Lazada;
    }

    public void setLazada(List<Rate> lazada) {
        Lazada = lazada;
    }

    private List<Rate> Tiki = new ArrayList<>();
    private List<Rate> Sendo = new ArrayList<>();
    private List<Rate> Shoppe = new ArrayList<>() ;
    private List<Rate> Lazada = new ArrayList<>();

    private Pane pane;
    private int stepPagination = 0;
    private int pageNumDefault = 5;
    private PrimaryController primaryController;

    @FXML
    void shopee_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        this.primaryController.setRatingAggregatorController(fxmlLoader.getController());
        if(this.primaryController.getRatingAggregatorController().getSendo().isEmpty()) {
            this.primaryController.getClient().SearchProductShopee(this.primaryController.getSearch_product().getText().split(" ")[0]);
            Product p = this.primaryController.getClient().ReceiveList().get(0);
            this.primaryController.getClient().GetReviewProductShopee(p.getproductID(),p.getIdshop());
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getClient().ReceiveListReviews();
            this.primaryController.getRatingAggregatorController().setShoppe(this.primaryController.getRatingAggregatorController().rateList);
        }
        else {
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getRatingAggregatorController().getShoppe();
        }
        this.primaryController.swapRatingAggregator();
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.shopee_button.getStyleClass().add("action");
    }

    @FXML
    void lazada_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        this.primaryController.setRatingAggregatorController(fxmlLoader.getController());
        if(this.primaryController.getRatingAggregatorController().getSendo().isEmpty()) {
            this.primaryController.getClient().SearchProductLazada(this.primaryController.getSearch_product().getText().split(" ")[0]);
            Product p = this.primaryController.getClient().ReceiveList().get(0);
            this.primaryController.getClient().GetReviewProductSendo(p.getproductID());
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getClient().ReceiveListReviews();
            this.primaryController.getRatingAggregatorController().setLazada(this.primaryController.getRatingAggregatorController().rateList);
        }
        else {
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getRatingAggregatorController().getLazada();
        }
        this.primaryController.swapRatingAggregator();
        this.shopee_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().add("action");
    }

    @FXML
    void sendo_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        this.primaryController.setRatingAggregatorController(fxmlLoader.getController());
        if(this.primaryController.getRatingAggregatorController().getSendo().isEmpty()) {
            this.primaryController.getClient().SearchProductSendo(this.primaryController.getSearch_product().getText().split(" ")[0]);
            Product p = this.primaryController.getClient().ReceiveList().get(0);
                this.primaryController.getClient().GetReviewProductSendo(p.getproductID());
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getClient().ReceiveListReviews();
            this.primaryController.getRatingAggregatorController().setSendo(this.primaryController.getRatingAggregatorController().rateList);
        }
        else {
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getRatingAggregatorController().getSendo();
        }
        this.primaryController.swapRatingAggregator();
        this.shopee_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().add("action");
    }

    @FXML
    void tiki_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        this.primaryController.setRatingAggregatorController(fxmlLoader.getController());
//        if(this.primaryController.getTiki().isEmpty()) {
            this.primaryController.getClient().SearchProduct(this.primaryController.getSearch_product().getText());
            Product p = this.primaryController.getClient().ReceiveList().get(0);
            this.primaryController.getClient().GetReviewProduct(p.getproductID());
            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getClient().ReceiveListReviews();
            this.primaryController.getRatingAggregatorController().setTiki(this.primaryController.getRatingAggregatorController().rateList);
//        }
//        else {
//            this.primaryController.getRatingAggregatorController().rateList = this.primaryController.getRatingAggregatorController().getTiki();
//        }
        this.primaryController.swapRatingAggregator();
        this.shopee_button.getStyleClass().remove("action");
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
                ratingAggregatorController.productRating_name_label.setText(this.primaryController.getItemListController().productList.get(0).getProductName());

                Image img = new Image(this.primaryController.getItemListController().productList.get(0).getImageUrl());
                ratingAggregatorController.ratingAgg_image.setImage(img);
                ratingAggregatorController.price_ratingAgg_label.setText("VNĐ "+String.valueOf(this.primaryController.getItemListController().productList.get(0).getPrice()));
                ratingAggregatorController.priceSale_ratingAgg_label.setText("VNĐ "+String.valueOf(this.primaryController.getItemListController().productList.get(0).getPrice_sale()));
                ratingAggregatorController.rating_average.setText(String.valueOf(this.primaryController.getItemListController().productList.get(0).getRating_average()));
                ratingAggregatorController.primaryController = primaryController1;
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
