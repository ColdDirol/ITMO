package com.backend.backend.security.controller;

import com.backend.backend.security.model.dto.JWTDto;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces("application/json")
@Consumes("application/json")
public class AuthController {

    @Inject
    UserService userService;

    @POST
    @Path("/registration")
    public Response registerUser(Users user) {
        System.out.println("Received user: " + user);
        try {
            String token = userService.register(user);
            JWTDto jwtDto = new JWTDto();
            jwtDto.setToken(token);

            return Response.status(Response.Status.CREATED).entity(jwtDto).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            System.err.println(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred during registration: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response loginUser (Users user) {
        try {
            String token = userService.login(user);
            JWTDto jwtDto = new JWTDto();
            jwtDto.setToken(token);
            return Response.ok(jwtDto).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(e.getMessage()).build();
        }
    }
}