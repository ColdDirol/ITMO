package com.backend.backend.service.impl;

import com.backend.backend.model.dto.MovieCsv;
import com.backend.backend.model.entities.Movie;
import com.backend.backend.model.entities.history.ExportHistory;
import com.backend.backend.model.entities.history.ImportHistory;
import com.backend.backend.model.parser.MovieParser;
import com.backend.backend.repository.impl.MovieRepositoryImpl;
import com.backend.backend.service.ExportHistoryService;
import com.backend.backend.service.ImportHistoryService;
import com.backend.backend.service.MovieService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieRepositoryImpl repository;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private MovieParser parser;

    @Inject
    private ImportHistoryService importHistoryService;

    @Inject
    private ExportHistoryService exportHistoryService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        Movie movie = findById(id);
        if (!securityContext.getUserPrincipal().getName().equals(movie.getCreatedUser())) {
            throw new ForbiddenException();
        }
        repository.deleteById(id);
    }

    @Override
    public Movie findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> findAll(Integer limit, Integer page) {
        return repository.findAll(limit, page);
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
        System.out.println("user1: " + securityContext.toString());
        System.out.println("user1 email: " + securityContext.getUserPrincipal().getName());
        movie.setCreatedUser(securityContext.getUserPrincipal().getName());
        System.out.println("user1: " + securityContext.toString());
        repository.save(movie);
    }

    public void importMovies(List<MovieCsv> movieCsvList) {
        List<Movie> movies = movieCsvList.stream()
                .map(parser::mapToMovie)
                .collect(Collectors.toList());

        movies.stream().findFirst().ifPresent(movie -> System.out.println("movies1: " + movie));

        repository.saveAll(movies);

        importHistoryService.save(
                new ImportHistory(
                    Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                    securityContext.getUserPrincipal().getName()
                )
        );
    }

    public List<MovieCsv> exportMovies() {
        List<MovieCsv> movieCsvList = repository.findAllByUser().stream()
                .map(MovieParser::mapToMovieCsv)
                .collect(Collectors.toList());

        exportHistoryService.save(
                new ExportHistory(
                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                        securityContext.getUserPrincipal().getName()
                )
        );

        return movieCsvList;
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