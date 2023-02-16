package org.cucumber.client.utils.classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.cucumber.client.MainMenuController;


import java.io.IOException;
import java.util.Optional;

public class FXUtils {

    /**
     * The prefix for all views titles.
     */
    private static final String titlePrefix = "Cucumber - ";

    /**
     * The suffix for all views files.
     */
    private static final String viewNameSuffix = ".fxml";

    private static Controller currentController = null;

    /**
     * Changes the current view to the given view.
     * @param viewName The name of the view to change to.
     * @param controllerContext The context of the current controller (`this`).
     * @param event The event that triggered the view change.
     */
    public static <T extends Controller> void goTo(
            String viewName,
            Object controllerContext,
            ActionEvent event
    ) throws IOException {

        FXMLLoader loader = new FXMLLoader(controllerContext.getClass().getResource(viewName + viewNameSuffix));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        currentController = loader.getController();

        appStage.setTitle(titlePrefix + currentController.getTitle());
        appStage.setResizable(false);
        appStage.setScene(scene);

        //onView Event trigger
        currentController.onView();
    }

    /**
     * Changes the current view to the given view.
     * @param viewName The name of the view to change to.
     * @param controllerContext The context of the current controller (`this`).
     * @param sourceScene The scene that triggered the view change.
     */
    public static <T extends Controller> void goTo(
            String viewName,
            Object controllerContext,
            Scene sourceScene
    ) throws IOException {

        FXMLLoader loader = new FXMLLoader(controllerContext.getClass().getResource(viewName + viewNameSuffix));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) sourceScene.getWindow();

        currentController = loader.getController();

        appStage.setTitle(titlePrefix + currentController.getTitle());
        appStage.setResizable(false);
        appStage.setScene(scene);

        getCurrentController(MainMenuController.class);
    }

    /**
     * get the controller actually in use by the interface
     * */
    public static Controller getCurrentController() {
        return currentController;
    }

    /**
     * Returns the current controller if it is of the given type.
     * @param controllerClass The type of the controller to return.
     */
    public static <T extends Controller> Optional<T> getCurrentController(Class<?> controllerClass) {
        return controllerClass.isInstance(currentController) ? Optional.of((T) currentController) : Optional.empty();
    }


}
