package com.backend.backend.model.dto;

import com.backend.backend.model.enumeration.ColorEnum;
import com.backend.backend.model.enumeration.MovieGenreEnum;
import com.backend.backend.model.enumeration.MpaaRatingEnum;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

@Data
public class MovieCsv {

    @CsvBindByName(column = "ID")
    private Long id;

    @CsvBindByName(column = "NAME")
    private String name;

    @CsvBindByName(column = "COORDINATE X")
    private Double coordinateX;

    @CsvBindByName(column = "COORDINATE Y")
    private Long coordinateY;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "CREATION DATE")
    private Date creationDate;

    @CsvBindByName(column = "OSCARS COUNT")
    private Integer oscarsCount;

    @CsvBindByName(column = "BUDGET")
    private Integer budget;

    @CsvBindByName(column = "TOTAL BOX OFFICE")
    private Integer totalBoxOffice;

    @CsvBindByName(column = "MPAA RATING")
    private MpaaRatingEnum mpaaRating;
//
    @CsvBindByName(column = "DIRECTOR NAME")
    private String directorName;

    @CsvBindByName(column = "DIRECTOR EYE COLOR")
    private ColorEnum directorEyeColor;

    @CsvBindByName(column = "DIRECTOR HAIR COLOR")
    private ColorEnum directorHairColor;

    @CsvBindByName(column = "DIRECTOR LOCATION X")
    private Float directorLocationX;

    @CsvBindByName(column = "DIRECTOR LOCATION Y")
    private Float directorLocationY;

    @CsvBindByName(column = "DIRECTOR LOCATION Z")
    private Integer directorLocationZ;

    @CsvBindByName(column = "DIRECTOR LOCATION NAME")
    private String directorLocationName;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "DIRECTOR BIRTHDAY")
    private Date directorBirthday;
//
    @CsvBindByName(column = "SCREENWRITER NAME")
    private String screenwriterName;

    @CsvBindByName(column = "SCREENWRITER EYE COLOR")
    private ColorEnum screenwriterEyeColor;

    @CsvBindByName(column = "SCREENWRITER HAIR COLOR")
    private ColorEnum screenwriterHairColor;

    @CsvBindByName(column = "SCREENWRITER LOCATION X")
    private Float screenwriterLocationX;

    @CsvBindByName(column = "SCREENWRITER LOCATION Y")
    private Float screenwriterLocationY;

    @CsvBindByName(column = "SCREENWRITER LOCATION Z")
    private Integer screenwriterLocationZ;

    @CsvBindByName(column = "SCREENWRITER LOCATION NAME")
    private String screenwriterLocationName;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "SCREENWRITER BIRTHDAY")
    private Date screenwriterBirthday;
//
    @CsvBindByName(column = "OPERATOR NAME")
    private String operatorName;

    @CsvBindByName(column = "OPERATOR EYE COLOR")
    private ColorEnum operatorEyeColor;

    @CsvBindByName(column = "OPERATOR HAIR COLOR")
    private ColorEnum operatorHairColor;

    @CsvBindByName(column = "OPERATOR LOCATION X")
    private Float operatorLocationX;

    @CsvBindByName(column = "OPERATOR LOCATION Y")
    private Float operatorLocationY;

    @CsvBindByName(column = "OPERATOR LOCATION Z")
    private Integer operatorLocationZ;

    @CsvBindByName(column = "OPERATOR LOCATION NAME")
    private String operatorLocationName;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "OPERATOR BIRTHDAY")
    private Date operatorBirthday;
//
    @CsvBindByName(column = "LENGTH")
    private Integer length;
    @CsvBindByName(column = "GOLDEN PALM COUNT")
    private Long goldenPalmCount;
    @CsvBindByName(column = "USA BOX OFFICE")
    private Long usaBoxOffice;
    @CsvBindByName(column = "TAGLINE")
    private String tagline;
    @CsvBindByName(column = "GENRE")
    private MovieGenreEnum genre;
}
