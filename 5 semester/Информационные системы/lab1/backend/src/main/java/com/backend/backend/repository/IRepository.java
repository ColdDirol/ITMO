package com.backend.backend.repository;

import java.util.List;

public interface IRepository<T, D> {

    void save(T coordinates);

    T findById(D id);

    List<T> findAll(Integer limit, Integer page);

    T update(T coordinates);

    void deleteById(D id);
}
