package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.Message;
import org.cucumber.server.repositories.IMessageRepository;

public class MessageRepository extends BasicRepository<Message> implements IMessageRepository {

    public MessageRepository(EntityManager em) {
        super(em, Message.class);
    }
}
