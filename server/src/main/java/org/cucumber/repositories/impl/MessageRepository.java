package org.cucumber.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.models.bo.Message;
import org.cucumber.repositories.IMessageRepository;

public class MessageRepository extends BasicRepository<Message> implements IMessageRepository {

    public MessageRepository(EntityManager em) {
        super(em, Message.class);
    }
}
