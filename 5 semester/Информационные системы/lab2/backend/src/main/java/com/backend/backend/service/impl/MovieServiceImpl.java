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
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Override
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

        Movie existingMovie = repository.findByName(movie.getName());
        if (existingMovie != null && !existingMovie.getId().equals(movie.getId())) {
            throw new IllegalArgumentException("A movie with the name '" + movie.getName() + "' already exists.");
        }

        return repository.update(movie);
    }

    private final Lock lock = new ReentrantLock();

    @Override
    public void save(Movie movie) {
        lock.lock();
        try {
            Movie existingMovie = repository.findByName(movie.getName());
            if (existingMovie != null) {
                throw new IllegalArgumentException("A movie with the name '" + movie.getName() + "' already exists.");
            }
            movie.setCreatedUser (securityContext.getUserPrincipal().getName());
            repository.save(movie);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public void saveAll(List<Movie> movies) {
        List<String> existingNames = repository.findAllNames();
        List<String> duplicateNames = movies.stream()
                .map(Movie::getName)
                .filter(existingNames::contains)
                .collect(Collectors.toList());

        if (!duplicateNames.isEmpty()) {
            throw new IllegalArgumentException(
                    "The following movie names already exist: " + String.join(", ", duplicateNames)
            );
        }

        String currentUser = securityContext.getUserPrincipal().getName();
        movies.forEach(movie -> movie.setCreatedUser(currentUser));

        repository.saveAll(movies);
    }

    public void importMovies(List<MovieCsv> movieCsvList) {
        List<Movie> movies = movieCsvList.stream()
                .map(parser::mapToMovie)
                .collect(Collectors.toList());

        saveAll(movies);

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