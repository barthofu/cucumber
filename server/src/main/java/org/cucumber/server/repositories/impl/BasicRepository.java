package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.repositories.IBasicRepository;

import java.util.List;

public abstract class BasicRepository<T> implements IBasicRepository<T> {

    private final EntityManager em;
    private final Class<T> targetClass;

    public BasicRepository(EntityManager em, Class<T> targetClass ) {
        this.em = em;
        this.targetClass = targetClass;
    }

    @Override
    public T findById(Integer id) {
        return em.find(targetClass, id);
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("SELECT t FROM " + targetClass.getSimpleName() + " t").getResultList();
    }

    @Override
    public T create(T obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    @Override
    public T update(T obj) {
        return em.merge(obj);
    }

    @Override
    public void delete(T obj) {
        em.remove(obj);
    }
}
