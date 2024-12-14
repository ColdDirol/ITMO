package com.backend.backend.service.impl;

import com.backend.backend.model.entities.history.ExportHistory;
import com.backend.backend.repository.impl.history.ExportHistoryRepositoryImpl;
import com.backend.backend.service.ExportHistoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class ExportHistoryServiceImpl implements ExportHistoryService {

    @Inject
    private ExportHistoryRepositoryImpl repository;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public void save(ExportHistory exportHistoryElement) {
        repository.save(exportHistoryElement);
    }

    @Override
    public List<ExportHistory> getHistory(Integer limit, Integer page) {
        if (securityContext.isUserInRole("EGOSHIN") || securityContext.isUserInRole("ADMIN")) {
            return repository.findAll(limit, page);
        } else {
            return repository.findAllByUser(limit, page);
        }
    }

    @Override
    public Long getCount() {
        if (securityContext.isUserInRole("EGOSHIN") || securityContext.isUserInRole("ADMIN")) {
            return repository.countAll();
        } else {
            return repository.countAllByUser();
        }
    }
}
