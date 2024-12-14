package com.backend.backend.repository.impl.history;

import com.backend.backend.model.entities.history.ImportHistory;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class ImportHistoryRepositoryImpl extends AbstractRepository<ImportHistory> {

    @Inject
    private SecurityContext securityContext;

    public ImportHistoryRepositoryImpl() {
        super(ImportHistory.class);
    }

    public List<ImportHistory> findAllByUser(Integer limit, Integer page) {
        return entityManager.createQuery("SELECT e FROM " + ImportHistory.class.getSimpleName() + " e WHERE e.createdUser  = :email", ImportHistory.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long countAll() {
        return entityManager.createQuery("SELECT COUNT(e) FROM " + ImportHistory.class.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }

    public Long countAllByUser() {
        return entityManager.createQuery("SELECT COUNT(e) FROM " + ImportHistory.class.getSimpleName() + " e WHERE e.createdUser = :email", Long.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .getSingleResult();
    }

}
