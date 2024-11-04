package com.backend.backend.controller;

import com.backend.backend.model.entities.Movie;
import com.backend.backend.service.impl.MovieServiceImpl;
import com.backend.backend.utils.QueryParamParser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/v1/movie")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieControllerV1 {

    @Inject
    private MovieServiceImpl service;

    @POST
    public Response createMovie(@Valid Movie movie) {
        try {
            service.save(movie);
            return Response.status(Response.Status.CREATED).entity(movie).build();
        } catch (Exception e) { // RollbackException + ConstraintViolationException
            return Response.status(Response.Status.BAD_REQUEST).entity("The data is not completely filled in. Please check the entered data.").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getMovie(@PathParam("id") Long id) {
        Movie movie = service.findById(id);
        if (movie != null) {
            return Response.ok(movie).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/count/all-by-user")
    public Long countAllByUser() {
        return service.countAllByUser();
    }

    @GET
    @Path("/count/all-public")
    public Long countAllPublic() {
        return service.countAllPublic();
    }

    @GET
    @Path("/all/{limit}/{page}")
    public List<Movie> getAll(
            @PathParam("limit")
            Integer limit,
            @PathParam("page")
            Integer page
    ) {
        return service.findAll(limit, page);
    }

    @GET
    @Path("/all-by-user/{limit}/{page}")
    public List<Movie> getAllByUser(
            @PathParam("limit")
            Integer limit,
            @PathParam("page")
            Integer page
    ) {
        return service.findAllByUser(limit, page);
    }

    @GET
    @Path("/all-public/{limit}/{page}")
    public List<Movie> getAllPublicMovies(
            @PathParam("limit")
            Integer limit,
            @PathParam("page")
            Integer page
    ) {
        return service.findAllPublic(limit, page);
    }

    @GET
    @Path("/all-by-user-filtered/{limit}/{page}")
    public List<Movie> getAllByUserFiltered(
            @PathParam("limit") Integer limit,
            @PathParam("page") Integer page,
            @QueryParam("filter") String filter,
            @QueryParam("columnWithRequest") String columnWithRequest
    ) {
        Map<String, String> columnMap = QueryParamParser.parseQueryParams(columnWithRequest);
        System.out.println("limit: " + limit);
        System.out.println("page: " + page);
        System.out.println("filter: " + filter);
        System.out.println("columnWithRequest: " + columnMap);
        return service.findAllByUserFiltered(limit, page, columnMap, filter);
    }

    @GET
    @Path("/all-public-filtered/{limit}/{page}")
    public List<Movie> getAllPublicFiltered(
            @PathParam("limit") Integer limit,
            @PathParam("page") Integer page,
            @QueryParam("filter") String filter,
            @QueryParam("columnWithRequest") String columnWithRequest
    ) {
        Map<String, String> columnMap = QueryParamParser.parseQueryParams(columnWithRequest);
        return service.findAllPublicFiltered(limit, page, columnMap, filter);
    }

    @PUT
    public Response updateMovie(@Valid Movie movie) {
        try {
            Movie updatedMovie = service.update(movie);
            if (updatedMovie != null) {
                return Response.ok(updatedMovie).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The data is not completely filled in. Please check the entered data.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(
            @PathParam("id")
            Long id
    ) {
        try {
            service.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).entity("This movie is not yours").build();
        }
    }
}
