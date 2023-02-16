package org.cucumber.server.utils.errors;

public class UsernameAlreadyTakenException extends Exception {

    public UsernameAlreadyTakenException() {
        super("Ce pseudo est déjà pris");
    }
}
