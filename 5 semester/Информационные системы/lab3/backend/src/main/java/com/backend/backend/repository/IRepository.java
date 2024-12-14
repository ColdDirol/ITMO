package com.backend.backend.repository;

import java.util.List;

public interface IRepository<T, D> {

    void save(T entityDto);

    void saveAll(List<T> entities);

    T findById(D id);

    List<T> findAll(Integer limit, Integer page);

    T update(T entityDto);

    void deleteById(D id);
}
