package org.cucumber.server.repositories;

import java.util.List;

public interface IBasicRepository<T> {

    T findById(Integer id);

    List<T> findAll();

    T create(T t);

    T update (T t);

    void delete(T t);

}
