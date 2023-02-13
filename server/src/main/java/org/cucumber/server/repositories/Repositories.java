package org.cucumber.server.repositories;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cucumber.server.repositories.impl.BasicRepository;
import org.cucumber.server.repositories.impl.MessageRepository;
import org.cucumber.server.repositories.impl.RoomRepository;
import org.cucumber.server.repositories.impl.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton which contains all the repositories
 */
public class Repositories {

    private final EntityManagerFactory entityManagerFactory;

    private final List<BasicRepository> repositories = new ArrayList<>();

    private Repositories() {

        this.entityManagerFactory = Persistence
                .createEntityManagerFactory("server");

        this.repositories.add(new UserRepository(this.entityManagerFactory.createEntityManager()));
        this.repositories.add(new MessageRepository(this.entityManagerFactory.createEntityManager()));
        this.repositories.add(new RoomRepository(this.entityManagerFactory.createEntityManager()));
    }

    // function to get a repository whose type is passed as a parameter
    public static <T extends BasicRepository> T get(Class<T> repositoryClass) {

        Repositories instance = getInstance();

        for (BasicRepository repository : instance.repositories) {
            if (repository.getClass().equals(repositoryClass)) {
                return (T) repository;
            }
        }

        return null;
    }

    // ====================
    // Singleton
    // ====================

    private static Repositories instance;

    public static Repositories getInstance() {
        if (instance == null) {
            instance = new Repositories();
        }
        return instance;
    }
}
