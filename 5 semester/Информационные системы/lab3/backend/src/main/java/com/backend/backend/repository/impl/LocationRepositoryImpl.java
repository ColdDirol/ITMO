package com.backend.backend.repository.impl;

import com.backend.backend.model.entities.Location;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocationRepositoryImpl extends AbstractRepository<Location> {

    public LocationRepositoryImpl() {
        super(Location.class);
    }
}
