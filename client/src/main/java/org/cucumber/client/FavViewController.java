package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;

import java.io.IOException;

public class FavViewController extends Controller {

    public FavViewController() {
        super("Mes favoris");
    }

    @FXML
    protected void onRetourButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.MAIN_MENU.getViewName(), this, event);
    }

    @Override
    public void onView() {

    }
}
