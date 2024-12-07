package com.backend.backend.repository.impl.history;

import com.backend.backend.model.entities.history.ExportHistory;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class ExportHistoryRepositoryImpl extends AbstractRepository<ExportHistory> {

    @Inject
    private SecurityContext securityContext;

    public ExportHistoryRepositoryImpl() {
        super(ExportHistory.class);
    }

    public List<ExportHistory> findAllByUser(Integer limit, Integer page) {
        return entityManager.createQuery("SELECT e FROM " + ExportHistory.class.getSimpleName() + " e WHERE e.createdUser  = :email", ExportHistory.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }
}
