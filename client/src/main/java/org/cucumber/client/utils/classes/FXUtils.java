package org.cucumber.client.utils.classes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class FXUtils {

    private static final String titlePrefix = "Cucumber - ";
    private static final String viewNameSuffix = ".fxml";

    public static <T extends Controller> void goTo(
            String viewName,
            Object controllerContext,
            ActionEvent event
    ) throws IOException {

        Parent parent = FXMLLoader.load(
                controllerContext.getClass().getResource(viewName + viewNameSuffix)
        );
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.setTitle(titlePrefix + ((T) controllerContext).getTitle());
        appStage.setResizable(false);
        appStage.setScene(scene);
    }

    public static <T extends Controller> void goTo(
            String viewName,
            Object controllerContext,
            Scene sourceScene
    ) throws IOException {

        Parent parent = FXMLLoader.load(
                controllerContext.getClass().getResource(viewName + viewNameSuffix)
        );
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) sourceScene.getWindow();

        appStage.setTitle(titlePrefix + ((T) controllerContext).getTitle());
        appStage.setResizable(false);
        appStage.setScene(scene);
    }


}
