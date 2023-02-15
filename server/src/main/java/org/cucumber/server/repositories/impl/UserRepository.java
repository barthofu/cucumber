package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.IUserRepository;

import java.util.Set;

public class UserRepository extends BasicRepository<User> implements IUserRepository {

    public UserRepository(EntityManager em) {
        super(em, User.class);
    }

    @Override
    public Set<User> getUserFav(User user) {
        return user.getFavorites();
    }

    @Override
    public void deleteFav(User user, Integer targetId) {
        em.getTransaction().begin();
        em.createNativeQuery(String.format("DELETE FROM user_favorite WHERE to = %d AND from = %d", targetId, user.getId()));
        em.getTransaction().commit();
    }

//
//    @Override
//    public void deleteFav(User source, Integer targetId) {
//            String hql = "delete from UserFavorite uf where uf.from.id = :sourceId and uf.to.id = :targetId";
//            em.getTransaction().begin();
//            em.createQuery(hql)
//                    .setParameter("sourceId", source.getId())
//                    .setParameter("targetId", targetId)
//                    .executeUpdate();
//            em.getTransaction().commit();
//    }
}
