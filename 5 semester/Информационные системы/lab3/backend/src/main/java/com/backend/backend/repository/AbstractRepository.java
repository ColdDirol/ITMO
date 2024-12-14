package com.backend.backend.repository;

import com.backend.backend.model.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class AbstractRepository<T> implements IRepository<T, Long> {

    @PersistenceContext(unitName = "default")
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    public List<Movie> findAllByName(String name) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.name = :name", Movie.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional
    public void saveAll(List<T> entities) {
        entities.forEach(entityManager::persist);
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll(Integer limit, Integer page) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        T entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
