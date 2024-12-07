package com.backend.backend.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Location {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Float x;

    @Column
    private Float y;

    @Column
    private Integer z;

    @Column(nullable = false)
    private String name; //Поле не может быть null

    public Location(Float x, Float y, Integer z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
