package com.backend.backend.model.entities.history;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "import_history")
@RequiredArgsConstructor
public class ImportHistory {

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

    @Column
    private UUID fileIndex;

    public ImportHistory(Date date, String createdUser, UUID fileIndex) {
        this.date = date;
        this.createdUser = createdUser;
        this.fileIndex = fileIndex;
    }
}
