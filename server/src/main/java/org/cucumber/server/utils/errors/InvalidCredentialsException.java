package org.cucumber.server.utils.errors;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Pseudo ou mot de passe incorrect");
    }
}
