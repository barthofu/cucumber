package org.cucumber.repositories;

import java.util.List;

public interface IBasicRepository<T> {

    T findById(Long id);

    List<T> findAll();

    T create(T t);

    T update (T t);

    void delete(T t);

}
