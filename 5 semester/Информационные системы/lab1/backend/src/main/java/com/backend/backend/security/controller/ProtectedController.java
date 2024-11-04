package com.backend.backend.security.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/protected")
public class ProtectedController {

    @GET
    public Response getProtectedResource(
            @Context
            SecurityContext securityContext
    ) {
        return Response.ok(securityContext.getUserPrincipal()).build();
    }
}