package com.backend.backend.service.impl;

import com.backend.backend.model.entities.history.ImportHistory;
import com.backend.backend.repository.impl.history.ImportHistoryRepositoryImpl;
import com.backend.backend.service.ImportHistoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class ImportHistoryServiceImpl implements ImportHistoryService {

    @Inject
    private ImportHistoryRepositoryImpl repository;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public void save(ImportHistory importHistoryElement) {
        repository.save(importHistoryElement);
    }

    public List<ImportHistory> getHistory(Integer limit, Integer page) {
        System.out.println("user1: role: " + securityContext.isUserInRole("ADMIN"));
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
