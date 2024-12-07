package com.backend.backend.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@RequiredArgsConstructor
public class Coordinates {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Поле x не может быть null")
    @Max(value = 7, message = "Максимальное значение поля x: 7")
    private Double x; //Максимальное значение поля: 7, Поле не может быть null

    @Column
    @Min(value = -487, message = "Значение поля y должно быть больше -487")
    private Long y; //Значение поля должно быть больше -487

    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }
}
