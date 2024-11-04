package com.backend.backend.repository.impl;

import com.backend.backend.model.entities.Coordinates;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoordinatesRepositoryImpl extends AbstractRepository<Coordinates> {

    public CoordinatesRepositoryImpl() {
        super(Coordinates.class);
    }
}
