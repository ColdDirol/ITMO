package com.backend.backend.model.parser;

import com.backend.backend.model.dto.MovieCsv;
import com.backend.backend.model.entities.Coordinates;
import com.backend.backend.model.entities.Location;
import com.backend.backend.model.entities.Movie;
import com.backend.backend.model.entities.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class MovieParser {

    @Inject
    private SecurityContext securityContext;

    public Movie mapToMovie(MovieCsv movieCsv) {
        Movie movie = new Movie();

        System.out.println("Coordinate X: " + movieCsv.getCoordinateX());
        System.out.println("Coordinate Y: " + movieCsv.getCoordinateY());

        movie.setName(movieCsv.getName());
        movie.setCoordinates(
                new Coordinates(
                        movieCsv.getCoordinateX(),
                        movieCsv.getCoordinateY()
                )
        );
        movie.setCreationDate(movieCsv.getCreationDate());
        movie.setOscarsCount(movieCsv.getOscarsCount());
        movie.setBudget(movieCsv.getBudget());
        movie.setTotalBoxOffice(movieCsv.getTotalBoxOffice());
        movie.setMpaaRating(movieCsv.getMpaaRating());

        movie.setDirector(
                new Person(
                        movieCsv.getDirectorName(),
                        movieCsv.getDirectorEyeColor(),
                        movieCsv.getDirectorHairColor(),
                        new Location(
                                movieCsv.getDirectorLocationX(),
                                movieCsv.getDirectorLocationY(),
                                movieCsv.getDirectorLocationZ(),
                                movieCsv.getDirectorLocationName()
                        ),
                        movieCsv.getScreenwriterBirthday()
                )
        );

        movie.setScreenwriter(
                new Person(
                        movieCsv.getScreenwriterName(),
                        movieCsv.getScreenwriterEyeColor(),
                        movieCsv.getScreenwriterHairColor(),
                        new Location(
                                movieCsv.getScreenwriterLocationX(),
                                movieCsv.getScreenwriterLocationY(),
                                movieCsv.getScreenwriterLocationZ(),
                                movieCsv.getScreenwriterLocationName()
                        ),
                        movieCsv.getScreenwriterBirthday()
                )
        );

        movie.setOperator(
                new Person(
                        movieCsv.getOperatorName(),
                        movieCsv.getOperatorEyeColor(),
                        movieCsv.getOperatorHairColor(),
                        new Location(
                                movieCsv.getOperatorLocationX(),
                                movieCsv.getOperatorLocationY(),
                                movieCsv.getOperatorLocationZ(),
                                movieCsv.getOperatorLocationName()
                        ),
                        movieCsv.getScreenwriterBirthday()
                )
        );

        movie.setLength(movieCsv.getLength());
        movie.setGoldenPalmCount(movieCsv.getGoldenPalmCount());
        movie.setUsaBoxOffice(movieCsv.getUsaBoxOffice());
        movie.setTagline(movieCsv.getTagline());
        movie.setGenre(movieCsv.getGenre());

        movie.setCreatedUser(securityContext.getUserPrincipal().getName());

        return movie;
    }

    public static MovieCsv mapToMovieCsv(Movie movie) {
        MovieCsv movieCsv = new MovieCsv();

        movieCsv.setId(movie.getId());
        movieCsv.setName(movie.getName());
        movieCsv.setCoordinateX(movie.getCoordinates().getX());
        movieCsv.setCoordinateY(movie.getCoordinates().getY());
        movieCsv.setCreationDate(movie.getCreationDate());
        movieCsv.setOscarsCount(movie.getOscarsCount());
        movieCsv.setBudget(movie.getBudget());
        movieCsv.setTotalBoxOffice(movie.getTotalBoxOffice());
        movieCsv.setMpaaRating(movie.getMpaaRating());

        if (movie.getDirector() != null) {
            movieCsv.setDirectorName(movie.getDirector().getName());
            movieCsv.setDirectorEyeColor(movie.getDirector().getEyeColor());
            movieCsv.setDirectorHairColor(movie.getDirector().getHairColor());
            movieCsv.setDirectorLocationX(movie.getDirector().getLocation().getX());
            movieCsv.setDirectorLocationY(movie.getDirector().getLocation().getY());
            movieCsv.setDirectorLocationZ(movie.getDirector().getLocation().getZ());
            movieCsv.setDirectorLocationName(movie.getDirector().getLocation().getName());
            movieCsv.setDirectorBirthday(movie.getDirector().getBirthday());
        }

        if (movie.getScreenwriter() != null) {
            movieCsv.setScreenwriterName(movie.getScreenwriter().getName());
            movieCsv.setScreenwriterEyeColor(movie.getScreenwriter().getEyeColor());
            movieCsv.setScreenwriterHairColor(movie.getScreenwriter().getHairColor());
            movieCsv.setScreenwriterLocationX(movie.getScreenwriter().getLocation().getX());
            movieCsv.setScreenwriterLocationY(movie.getScreenwriter().getLocation().getY());
            movieCsv.setScreenwriterLocationZ(movie.getScreenwriter().getLocation().getZ());
            movieCsv.setScreenwriterLocationName(movie.getScreenwriter().getLocation().getName());
            movieCsv.setScreenwriterBirthday(movie.getScreenwriter().getBirthday());
        }

        if (movie.getOperator() != null) {
            movieCsv.setOperatorName(movie.getOperator().getName());
            movieCsv.setOperatorEyeColor(movie.getOperator().getEyeColor());
            movieCsv.setOperatorHairColor(movie.getOperator().getHairColor());
            movieCsv.setOperatorLocationX(movie.getOperator().getLocation().getX());
            movieCsv.setOperatorLocationY(movie.getOperator().getLocation().getY());
            movieCsv.setOperatorLocationZ(movie.getOperator().getLocation().getZ());
            movieCsv.setOperatorLocationName(movie.getOperator().getLocation().getName());
            movieCsv.setOperatorBirthday(movie.getOperator().getBirthday());
        }

        movieCsv.setLength(movie.getLength());
        movieCsv.setGoldenPalmCount(movie.getGoldenPalmCount());
        movieCsv.setUsaBoxOffice(movie.getUsaBoxOffice());
        movieCsv.setTagline(movie.getTagline());
        movieCsv.setGenre(movie.getGenre());

        return movieCsv;
    }
}
