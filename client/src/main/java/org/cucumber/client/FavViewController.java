package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cucumber.client.utils.classes.FXUtils;

import java.io.IOException;

public class FavViewController {

    @FXML
    protected void onRetourButton(ActionEvent event) throws IOException {
        FXUtils.goToMainMenu(this, event);
    }
}
