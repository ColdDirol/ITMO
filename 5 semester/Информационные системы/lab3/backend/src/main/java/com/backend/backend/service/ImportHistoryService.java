package com.backend.backend.service;

import com.backend.backend.model.entities.history.ImportHistory;

import java.util.List;

public interface ImportHistoryService {

    void save(ImportHistory importHistoryElement);

    List<ImportHistory> getHistory(Integer limit, Integer page);

    Long getCount();
}
