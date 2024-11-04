package com.backend.backend.service.impl;

import com.backend.backend.model.entities.Movie;
import com.backend.backend.repository.impl.MovieRepositoryImpl;
import com.backend.backend.security.repository.UserRepositoryImpl;
import com.backend.backend.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MovieServiceImpl extends AbstractService<Movie, MovieRepositoryImpl> {

    @Inject
    private MovieRepositoryImpl repository;

    @Inject
    private UserRepositoryImpl userRepository;

    @Inject
    private SecurityContext securityContext;

    @Override
    @Transactional
    public void deleteById(Long id) {
        Movie movie = findById(id);
        if (!securityContext.getUserPrincipal().getName().equals(movie.getCreatedUser())) {
            throw new ForbiddenException();
        }
        repository.deleteById(id);
    }

    @Transactional
    public Movie update(Movie movie) {
        Movie oldMovie = findById(movie.getId());
        movie.setCreatedUser(oldMovie.getCreatedUser());
        return repository.update(movie);
    }

    @Override
    @Transactional
    public void save(Movie movie) {
        movie.setCreatedUser(securityContext.getUserPrincipal().getName());
        repository.save(movie);
    }

    public Long countAllByUser() {
        return repository.countAllByUser();
    }

    public Long countAllPublic() {
        return repository.countAllPublic();
    }

    public List<Movie> findAllByUser(Integer limit, Integer page) {
        return repository.findAllByUser(limit, page);
    }

    public List<Movie> findAllPublic(Integer limit, Integer page) {
        return repository.findAllPublic(limit, page);
    }

    public List<Movie> findAllByUserFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter) {
        return repository.findAllByUserFiltered(limit, page, columnWithRequest, filter);
    }

    public List<Movie> findAllPublicFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter) {
        return repository.findAllPublicFiltered(limit, page, columnWithRequest, filter);
    }
}