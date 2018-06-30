package jp.co.afis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String TITLE = "AFIS -A Fantastic Invertible Shougi- ver0.7.0";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layout/main.fxml"));
        primaryStage.setTitle(TITLE);
        Scene scene = new Scene(root, 1280, 760);
        //scene.getStylesheets().add(getClass().getResource("basic.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
