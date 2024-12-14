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
import jakarta.annotation.Resource;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Produces
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @Resource(lookup = "java:jboss/datasources/PostgresDS")
    private DataSource dataSource;

    @Override
    public void deleteById(Long id) throws SQLException {
        Movie movie = findById(id);
        if (!securityContext.getUserPrincipal().getName().equals(movie.getCreatedUser())) {
            throw new ForbiddenException();
        }

        if (repository.findAllByName(movie.getName()).size() > 1) {
            throw new IllegalArgumentException("Elements more than 1");
        }

        Connection connection = dataSource.getConnection();
        try (connection) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("DELETE transaction isolation level: " + connection.getTransactionIsolation());

            userTransaction.begin();
            repository.deleteById(id);
            userTransaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public Movie update(Movie movie) throws SQLException {
        Movie oldMovie = findById(movie.getId());

        if (repository.findAllByName(movie.getName()).size() > 1) {
            throw new IllegalArgumentException("Elements more than 1");
        }

        movie.setCreatedUser(oldMovie.getCreatedUser());

        Movie existingMovie = repository.findByName(movie.getName());
        if (existingMovie != null && !existingMovie.getId().equals(movie.getId())) {
            throw new IllegalArgumentException("A movie with the name '" + movie.getName() + "' already exists.");
        }
        if (!Objects.equals(existingMovie.getCreatedUser(), securityContext.getUserPrincipal().getName()) && securityContext.isUserInRole("ADMIN")) {
            throw new ForbiddenException("Element is not yours");
        }

        Connection connection = dataSource.getConnection();

        Movie newMovie = null;

        try (connection) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("UPDATE transaction isolation level: " + connection.getTransactionIsolation());

            userTransaction.begin();
            newMovie = repository.update(movie);
            userTransaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newMovie;
    }

    private final Lock lock = new ReentrantLock();

    @Override
    public void save(Movie movie) throws IllegalArgumentException {
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
    public void saveAll(List<Movie> movies) throws SystemException, SQLException {
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

        Connection connection = dataSource.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        System.out.println("Current transaction isolation level: " + connection.getTransactionIsolation());

        try {
            userTransaction.begin();
            System.out.println("Transaction status: " + userTransaction.getStatus());
            movies.forEach(this::save);
            userTransaction.commit();
        } catch (IllegalArgumentException e) {
            userTransaction.rollback();
            throw new BadRequestException(
                    "Some attributes from the importing CSV file have the same name!"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    public void importMovies(List<MovieCsv> movieCsvList) {
        List<Movie> movies = movieCsvList.stream()
                .map(parser::mapToMovie)
                .collect(Collectors.toList());

        try {
            saveAll(movies);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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