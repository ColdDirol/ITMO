package com.backend.backend.service.impl;

import com.backend.backend.minio.MinioService;
import com.backend.backend.model.dto.MovieCsv;
import com.backend.backend.model.entities.Movie;
import com.backend.backend.model.entities.history.ExportHistory;
import com.backend.backend.model.entities.history.ImportHistory;
import com.backend.backend.model.parser.MovieParser;
import com.backend.backend.repository.impl.MovieRepositoryImpl;
import com.backend.backend.service.ExportHistoryService;
import com.backend.backend.service.ImportHistoryService;
import com.backend.backend.service.MovieService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.annotation.Resource;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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

    @Inject
    private MinioService minioService;

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

//        Connection connection = dataSource.getConnection();
//        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//        System.out.println("SAVE ALL transaction isolation level: " + connection.getTransactionIsolation());

        try {
//            userTransaction.begin();
//            System.out.println("Transaction status: " + userTransaction.getStatus());
            movies.forEach(this::save);
//            userTransaction.commit();
        } catch (IllegalArgumentException e) {
//            userTransaction.rollback();
            throw new BadRequestException(
                    "Attributes in the importing CSV file have the same name!"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void importMovies(List<MovieCsv> movieCsvList) {
//        List<Movie> movies = movieCsvList.stream()
//                .map(parser::mapToMovie)
//                .collect(Collectors.toList());
//
//        try {
//            saveAll(movies);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        importHistoryService.save(
//                new ImportHistory(
//                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
//                        securityContext.getUserPrincipal().getName()
//                )
//        );
    }

    @Transactional
    public Response importMoviesStream(InputStream inputStream) throws Exception {
        UUID fileIndex = UUID.randomUUID();

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             InputStreamReader reader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8)) {

            bufferedInputStream.mark(Integer.MAX_VALUE);

            CsvToBean<MovieCsv> csvToBean = new CsvToBeanBuilder<MovieCsv>(reader)
                    .withType(MovieCsv.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MovieCsv> movieCsvList = csvToBean.parse();

            if (movieCsvList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No valid movie data found in the uploaded file.")
                        .build();
            }

            List<Movie> movies = movieCsvList.stream()
                    .map(parser::mapToMovie)
                    .collect(Collectors.toList());

            saveAll(movies);

            importHistoryService.save(
                    new ImportHistory(
                            Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                            securityContext.getUserPrincipal().getName(),
                            fileIndex
                    )
            );

            String objectName = fileIndex + ".csv";
            bufferedInputStream.reset();

            if (!minioService.uploadUncommitedFile(fileIndex, bufferedInputStream, bufferedInputStream.available())) {
                throw new RuntimeException("Failed to upload movie file");
            }

            minioService.commitFile(fileIndex);

            return Response.ok("Movies imported successfully, total: " + movieCsvList.size())
                    .header("MinIO-Object-Name", objectName)
                    .build();
        } catch (Exception e) {
            minioService.rollbackFile(fileIndex);
            throw new RuntimeException(e);
        }
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

    public Response importMoviesFromHistory(UUID fileIndex) {
        try {
            // Получаем InputStream для файла CSV из MinIO
            InputStream fileInputStream = minioService.getFile(fileIndex.toString() + ".csv");

            // Проверяем, что файл не пустой
            if (fileInputStream == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Файл не найден")
                        .build();
            }

            // Возвращаем CSV в ответе с InputStream
            return Response.ok(fileInputStream)
                    .header("Content-Disposition", "attachment; filename=\"movies.csv\"")
                    .header("Object-Name", "archive-"+fileIndex+".csv")
                    .header("Content-Type", "application/octet-stream") // Устанавливаем тип контента для файла
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Произошла ошибка при экспорте CSV")
                    .build();
        }
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