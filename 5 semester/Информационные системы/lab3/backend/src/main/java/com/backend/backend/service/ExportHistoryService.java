package com.backend.backend.service;

import com.backend.backend.model.entities.history.ExportHistory;

import java.util.List;

public interface ExportHistoryService {

    void save(ExportHistory exportHistoryElement);

    List<ExportHistory> getHistory(Integer limit, Integer page);

    Long getCount();
}
