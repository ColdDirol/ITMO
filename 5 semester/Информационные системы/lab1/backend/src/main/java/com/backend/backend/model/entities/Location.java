package com.backend.backend.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}
