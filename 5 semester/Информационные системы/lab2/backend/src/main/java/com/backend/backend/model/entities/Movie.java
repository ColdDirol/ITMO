package com.backend.backend.model.entities;

import com.backend.backend.model.enumeration.MovieGenreEnum;
import com.backend.backend.model.enumeration.MpaaRatingEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Movie {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    // Business logic UNIQUE
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(nullable = false)
    private Date creationDate; //Поле не может быть null

    @Column(nullable = true)
    @Min(value = 1, message = "Значение поля oscarsCount должно быть больше 0")
    private Integer oscarsCount; //Значение поля должно быть больше 0

    @Column(nullable = false)
    @Min(value = 1, message = "Значение поля budget должно быть больше 0")
    private Integer budget; //Значение поля должно быть больше 0, Поле не может быть null

    @Column
    @Min(value = 1, message = "Значение поля totalBoxOffice должно быть больше 0")
    private Integer totalBoxOffice; //Значение поля должно быть больше 0

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MpaaRatingEnum mpaaRating; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "director_id", nullable = true)
    private Person director; //Поле может быть null

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "screenwriter_id", nullable = false)
    private Person screenwriter;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "operator_id", nullable = true)
    private Person operator; //Поле может быть null

    @Column
    @Min(value = 1, message = "Значение поля length должно быть больше 0")
    private Integer length; //Значение поля должно быть больше 0

    @Column(nullable = true)
    @Min(value = 1, message = "Значение поля goldenPalmCount должно быть больше 0")
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null

    @Column(nullable = false)
    @Min(value = 1, message = "Значение поля usaBoxOffice должно быть больше 0")
    private Long usaBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0

    @Column(nullable = false)
    private String tagline; //Поле не может быть null

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private MovieGenreEnum genre; //Поле может быть null


    // custom:
    @Column
    private String createdUser;

    @Column
    private Boolean isPublic;
}
