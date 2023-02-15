package org.cucumber.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cucumber.client.services.SocketService;

import java.io.IOException;

public class Cucumber extends Application {

    public static int port = 3000;
    public static String address = "localhost";

    private static final String firstViewName = "login-view.fxml";
    private static final String firstViewTitle = "Cucumber - Se connecter";

    @Override
    public void start(Stage stage) throws IOException {

        // load the first view
        FXMLLoader fxmlLoader = new FXMLLoader(Cucumber.class.getResource(Cucumber.firstViewName));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // set the first view properties
        stage.setTitle(Cucumber.firstViewTitle);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(650);
        stage.resizableProperty().set(false);
        stage.show();

        // open a websocket connection to the server
        SocketService.getInstance().openConnection(address, port);
    }

    public static void main(String[] args) {
        launch();
    }
}
