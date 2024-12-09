package com.backend.backend.service;

import com.backend.backend.model.dto.MovieCsv;
import com.backend.backend.model.entities.Movie;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MovieService {

    void deleteById(Long id);

    Movie findById(Long id);

    List<Movie> findAll(Integer limit, Integer page);

    Movie update(Movie movie);

    void save(Movie movie) throws SQLException;

    void importMovies(List<MovieCsv> movieCsvList);

    List<MovieCsv> exportMovies();

    Long countAllByUser();

    Long countAllPublic();

    List<Movie> findAllByUser(Integer limit, Integer page);

    List<Movie> findAllPublic(Integer limit, Integer page);

    List<Movie> findAllByUserFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter);

    List<Movie> findAllPublicFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter);
}
