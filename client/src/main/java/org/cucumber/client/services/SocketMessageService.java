package org.cucumber.client.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.interfaces.IMessageCallback;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

/**
 * The SocketMessageService class is responsible for managing the messages with the server.
 */
public class SocketMessageService {

    /**
     * The timeout in milliseconds for a response.
     */
    private static final Long timeoutMillis = 10000L;

    private final Map<String, SocketMessageContent> responseQueue = new HashMap<>();

    /**
     * Send messages to the server and wait for a response.
     * After the response has been received, the provided callback function is called.
     * @param message the message to send
     * @param callback the callback function
     * @param context the context of the callback function (controller class)
     */
    public <T extends Controller> void send(SocketMessage message, IMessageCallback callback, T context) throws IOException {

        // send message to server
        String requestId = message.getId();
        SocketService.getInstance().sendToServer(message);

        // start a new thread to wait for response
        new Thread(() -> {
            // wait for response
            long startTime = System.currentTimeMillis();

            while (!isResponseReceived(requestId) || isExpired(startTime)) {
                // wait
                try {
                    Thread.sleep(15L);
                } catch (InterruptedException err) {
                    break;
                }
            }
            if (isResponseReceived(requestId)) {
                Logger.log(LoggerStatus.INFO, String.format("Response on %s has been resolved", message.getRoute()));
                callback.apply(getResponseContent(requestId), context);
            } else {
                Logger.log(LoggerStatus.SEVERE, String.format("Response on %s has expired", message.getRoute()));
            }
        }).start();
    }

    public SocketMessageContent getResponseContent(String requestId) {
        return responseQueue.get(requestId);
    }

    public void receive(String requestId, SocketMessageContent content) {
        responseQueue.put(requestId, content);
    }

    private boolean isResponseReceived(String requestId) {
        return responseQueue.containsKey(requestId);
    }

    private boolean isExpired(Long startTime) {
        return System.currentTimeMillis() >= startTime + timeoutMillis;
    }

    // ================================
    // Singleton
    // ================================

    private static SocketMessageService instance;

    private SocketMessageService() {}

    public static SocketMessageService getInstance() {
        if (instance == null) {
            instance = new SocketMessageService();
        }
        return instance;
    }
}
