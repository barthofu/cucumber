package org.cucumber.client.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.cucumber.client.Client;
import org.cucumber.client.utils.interfaces.IMessageCallback;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

public class MessageManager {

    private static final Long timeoutMillis = 10000L;

    private final Map<String, SocketMessageContent> responseQueue = new HashMap<>();

    private static MessageManager instance;

    private MessageManager() {
    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public void send(SocketMessage message, IMessageCallback callback) throws IOException {
        String requestId = message.getId();
        Client.getInstance().send(message);

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
                callback.apply(getResponseContent(requestId));
            } else {
                Logger.log(LoggerStatus.SEVERE, "response [%s] has expired");
            }
        }).start();
    }
    public void send(SocketMessage message, IMessageCallback callback, IMessageCallback failedCallback) throws IOException {
        String requestId = message.getId();
        Client.getInstance().send(message);

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
                callback.apply(getResponseContent(requestId));
            } else {
                Logger.log(LoggerStatus.SEVERE, "response [%s] has expired");
                failedCallback.apply(getResponseContent(requestId));
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
}
