package com.example.a7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Main Application!");
        stage.setScene(scene);
        stage.show();
    }

    public static void startMainWindow(){
        MainWindow newWindow = new MainWindow();
        try {
            newWindow.start(new Stage());
        }
        catch (IOException e) {
            displayAlert(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void displayAlert(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error!");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}