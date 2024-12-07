package com.backend.backend.model.entities;

import com.backend.backend.model.enumeration.ColorEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
public class Person {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum eyeColor; //Поле не может быть null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum hairColor; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; //Поле не может быть null

    @Column(nullable = false)
    private Date birthday; //Поле может быть null

    public Person(String name, ColorEnum eyeColor, ColorEnum hairColor, Location location, Date birthday) {
        this.name = name;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
        this.birthday = birthday;
    }
}
