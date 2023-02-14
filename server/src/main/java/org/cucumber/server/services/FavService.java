package org.cucumber.server.services;

import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.Repositories;

import java.util.List;
import java.util.Set;

public class FavService {

    public void addUserToFav(User from, User to){

    }

    public void removeUserFromFav(User owner, User toRemove){

    }
    
    // ================================
    // Singleton
    // ================================

    private static FavService instance;

    private FavService() {}

    public static FavService getInstance() {
        if (instance == null) {
            instance = new FavService();
        }
        return instance;
    }

}
