package com.review;

import com.review.controllers.PrimaryController;
import com.review.models.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    private static Client client;
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        root.getStylesheets().add("stylesheets.css");
        stage.setTitle("Product Review App");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,ClassNotFoundException {
        client=new Client(1234,"localhost");
        client.ConnectClient();
        client.SearchProduct("ipad");
        PrimaryController.SetClient(client);
        launch();
    }

}