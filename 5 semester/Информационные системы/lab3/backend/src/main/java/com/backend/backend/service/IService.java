package com.backend.backend.service;

import java.util.List;

public interface IService<T, D> {

    void save(T t);

    T findById(D id);

    List<T> findAll(Integer limit, Integer page);

    T update(T t);

    void deleteById(D id);
}