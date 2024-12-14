package com.backend.backend.controller;

import com.backend.backend.model.entities.history.ExportHistory;
import com.backend.backend.model.entities.history.ImportHistory;
import com.backend.backend.service.ExportHistoryService;
import com.backend.backend.service.ImportHistoryService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/v1/import-export")
@RequestScoped
@Produces("application/json")
@Consumes("application/json")
public class ImportExportHistoryControllerV1 {

    @Inject
    private ImportHistoryService importHistoryService;

    @Inject
    private ExportHistoryService exportHistoryService;

    @GET
    @Path("/import/{limit}/{page}")
    public List<ImportHistory> getImports(
            @PathParam("limit")
            Integer limit,
            @PathParam("page")
            Integer page
    ) {
        return importHistoryService.getHistory(limit, page);
    }

    @GET
    @Path("/import/count")
    public Long getImportsCount() {
        return importHistoryService.getCount();
    }

    @GET
    @Path("/export/{limit}/{page}")
    public List<ExportHistory> getExports(
            @PathParam("limit")
            Integer limit,
            @PathParam("page")
            Integer page
    ) {
        return exportHistoryService.getHistory(limit, page);
    }

    @GET
    @Path("/export/count")
    public Long getExportsCount() {
        return exportHistoryService.getCount();
    }

}
