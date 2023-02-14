package org.cucumber.client.utils.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Controller {

    private String title;

    public abstract void onView();
}
