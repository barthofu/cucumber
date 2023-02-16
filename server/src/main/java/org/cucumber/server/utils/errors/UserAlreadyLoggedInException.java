package org.cucumber.server.utils.errors;

public class UserAlreadyLoggedInException extends Exception {

    public UserAlreadyLoggedInException() {
        super("Utilisateur déjà connecté");
    }
}
