package org.cucumber.client.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.cucumber.client.utils.interfaces.IMessageCallback;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;

public class MessageManager {

    private static final Long timeoutMillis = 10000L;

    private Map<String, SocketMessageContent> responseQueue = new HashMap<>();

    private static MessageManager instance;

    private MessageManager() {
    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public void send(
            String route,
            SocketMessage message,
            IMessageCallback callback
    ) {

        String requestId = UUID.randomUUID().toString();

        // TODO:send message to server on route

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
            // expired
        }


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

    private boolean isExpired(Long startTime){
        return System.currentTimeMillis() >= startTime + timeoutMillis;
    }

}
