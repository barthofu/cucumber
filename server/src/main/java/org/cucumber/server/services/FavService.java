package org.cucumber.server.services;

import org.cucumber.server.models.bo.User;

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
