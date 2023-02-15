package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.dto.generics.Empty;
import org.cucumber.common.dto.responses.GetFavOfUserResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.util.*;

public class FavViewController extends Controller {

    @FXML
    Label e1Label;
    @FXML
    Button e1Button;


    @FXML
    Label e2Label;
    @FXML
    Button e2Button;


    @FXML
    Label e3Label;
    @FXML
    Button e3Button;


    public FavViewController() {
        super("Mes favoris");
    }

    @FXML
    protected void onRetourButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.MAIN_MENU.getViewName(), this, event);
    }

    @Override
    public void onView() {
        try {
            SocketMessageService.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            Routes.Server.FAV_GET.getValue(),
                            new Empty()
                    ),
                    FavViewController::handleGetFavResponse,
                    this
            );
        } catch (Exception e) {
            Logger.log(LoggerStatus.SEVERE, "can't get favs " + e.getMessage());
        }
    }

    public static <T extends Controller> void handleGetFavResponse(SocketMessageContent socketMessageContent, T context) {
        Set<UserDTO> userDTOSet = ((GetFavOfUserResponse) socketMessageContent).getUserDTOSet();
        Iterator<UserDTO> iterator = userDTOSet.iterator();
        FavViewController fvc = (FavViewController) context;

        LinkedHashMap<Label, Button> slots = new LinkedHashMap<>();
        //no fancy constructor for LinkedHashMap
        slots.put(fvc.e1Label, fvc.e1Button);
        slots.put(fvc.e2Label, fvc.e2Button);
        slots.put(fvc.e3Label, fvc.e3Button);

        slots.forEach((label, btn) -> {
            if (iterator.hasNext()) {
                UserDTO userDTO = iterator.next();
                Platform.runLater(() -> {
                    label.setText(String.format("%s : %s",
                            userDTO.getUsername(),
                            userDTO.getAge())
                    );
                    btn.setDisable(false);
                    btn.setOnAction(event -> {
                        ((FavViewController) context).removeUserFromFav(userDTO);
                        btn.setDisable(true); //can't multiply request, anyway we'll refresh the full view after
                    });
                });
            } else {
                Platform.runLater(() -> {
                    label.setText("Emplacement Vide");
                    btn.setDisable(true);
                });
            }
        });
    }

    public void removeUserFromFav(UserDTO user) {
        try {
            SocketMessageService.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            Routes.Server.FAV_REMOVE.getValue(),
                            new UserTarget(user)
                    ),
                    FavViewController::handleRmFavResponse,
                    this
            );
        } catch (Exception e) {
            Logger.log(LoggerStatus.SEVERE, "can't rm fav : " + e.getMessage());
        }
    }

    public static <T extends Controller> void handleRmFavResponse(SocketMessageContent socketMessageContent, T context) {
        //update the list
        context.onView();
    }
}
