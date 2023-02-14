package org.cucumber.client.services;

public class MessageService {

    // ================================
    // Singleton
    // ================================

    private static MessageService instance;

    private MessageService() {}

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }
}
