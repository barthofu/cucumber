package org.cucumber.client.utils.classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cucumber.client.LoginController;

import java.io.IOException;

public class FXUtils {
    public static String mainMenuViewName = "main-menu-view.fxml";
    public static String favView = "fav-view.fxml";
    public static String loginViewName = "login-view.fxml";
    public static String registerViewName = "register-view.fxml";

    public static void goToMainMenu(Object o, ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(o.getClass().getResource(FXUtils.mainMenuViewName));
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("Cucumber - La vitesse rencontre l'amour!");
        appStage.setResizable(false);
        appStage.setScene(scene);
    }

    public static void goToFav(Object o, ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(o.getClass().getResource(FXUtils.favView));
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("Cucumber - Mes favoris");
        appStage.setResizable(false);
        appStage.setScene(scene);
    }

    public static void goToLogin(Object o, ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(o.getClass().getResource(FXUtils.loginViewName));
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("Cucumber - Se connecter");
        appStage.setResizable(false);
        appStage.setScene(scene);
    }

    public static void goToSignup(Object o, ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(o.getClass().getResource(FXUtils.registerViewName));
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("Cucumber - S'enregistrer");
        appStage.setResizable(false);
        appStage.setScene(scene);
    }
}
