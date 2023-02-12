package org.cucumber.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cucumber.client.utils.classes.FXUtils;

import java.io.IOException;

public class Cucumber extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cucumber.class.getResource(FXUtils.loginViewName));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Cucumber - Se connecter");
        stage.setScene(scene);
        stage.resizableProperty().set(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}