package com.backend.backend.model.entities.history;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "export_history")
@RequiredArgsConstructor
public class ExportHistory {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Date date;

    @Column
    private String createdUser;

    public ExportHistory(Date date, String createdUser) {
        this.date = date;
        this.createdUser = createdUser;
    }
}
