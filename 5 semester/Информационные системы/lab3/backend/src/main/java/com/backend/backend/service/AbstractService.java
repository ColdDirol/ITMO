package com.backend.backend.service;

import com.backend.backend.repository.AbstractRepository;
import com.backend.backend.security.repository.UserRepositoryImpl;
import com.backend.backend.security.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class AbstractService<T, R extends AbstractRepository<T>> implements IService<T, Long> {

    @Inject
    private R repository;

    @Override
    @Transactional
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll(Integer limit, Integer page) {
        return repository.findAll(limit, page);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return repository.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}