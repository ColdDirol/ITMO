package com.backend.backend.security.controller;

import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.enumeration.AdminRequestStatusEnum;
import com.backend.backend.security.repository.AdminRequestsRepository;
import com.backend.backend.security.service.AdminRequestsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/admin")
@Produces("application/json")
@Consumes("application/json")
public class AdminController {

    @Inject
    private AdminRequestsService adminRequestsService;

    @GET
    @Path("/requests")
    public Response getPendingRequests() {
        return Response.ok(
                adminRequestsService.getPendingRequestsForCurrentUser()
        ).build();
    }

    @GET
    @Path("/count/requests")
    public Response countPendingRequests() {
        return Response.ok(
                adminRequestsService.getPendingRequestsForCurrentUser().size()
        ).build();
    }

    @POST
    @Path("/requests/{id}/approve")
    public Response approveRequest(@PathParam("id") Long requestId) {
        adminRequestsService.approveRequest(requestId);
        return Response.ok().build();
    }

    @POST
    @Path("/requests/{id}/reject")
    public Response rejectRequest(@PathParam("id") Long requestId) {
        adminRequestsService.approveRequest(requestId);
        return Response.ok().build();
    }

    @GET
    @Path("/requests/status")
    public Response getRequestStatus() {
        return Response.ok(adminRequestsService.getRequestStatus()).build();
    }
}