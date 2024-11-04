package com.backend.backend.repository.impl;

import com.backend.backend.model.entities.Person;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepositoryImpl extends AbstractRepository<Person> {

    public PersonRepositoryImpl() {
        super(Person.class);
    }
}
