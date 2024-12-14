package com.backend.backend.security.controller;

import com.backend.backend.security.repository.AdminVotesRepository;
import com.backend.backend.security.service.AdminVotesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/vote")
@Produces("application/json")
@Consumes("application/json")
public class AdminVotesController {

    @Inject
    private AdminVotesService adminVotesService;

    @POST
    @Path("/approve/{id}")
    public Response approveRequest(@PathParam("id") Long requestId) {
        adminVotesService.approve(requestId);
        return Response.ok().build();
    }

    @POST
    @Path("/reject/{id}")
    public Response rejectRequest(@PathParam("id") Long requestId) {
        adminVotesService.reject(requestId);
        return Response.ok().build();
    }
}
