package org.cucumber.client.utils.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * basic view controller, containing base methods
 * */
@Getter
@AllArgsConstructor
public abstract class Controller {

    private String title;

    public abstract void onView();
}
