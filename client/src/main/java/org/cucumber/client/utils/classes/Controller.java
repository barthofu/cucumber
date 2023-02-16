package org.cucumber.client.utils.classes;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Basic view controller, containing base methods
 * */
@Getter
@AllArgsConstructor
public abstract class Controller {

    private String title;

    /**
     * Called when the view is initialized using {@link FXUtils#goTo(String, Object, ActionEvent)} or {@link FXUtils#goTo(String, Object, Scene)} functions
     */
    public void onInit() {};

    /**
     * Called when the view is initialized using {@link FXUtils#goTo(String, Object, ActionEvent)} or {@link FXUtils#goTo(String, Object, Scene)} functions
     */
    public void onDestroy() {};
}
