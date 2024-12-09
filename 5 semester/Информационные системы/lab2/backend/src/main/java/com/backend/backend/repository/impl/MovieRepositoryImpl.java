package com.backend.backend.repository.impl;

import com.backend.backend.model.dto.MovieCsv;
import com.backend.backend.model.entities.Movie;
import com.backend.backend.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MovieRepositoryImpl extends AbstractRepository<Movie> {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Inject
    private SecurityContext securityContext;

    public MovieRepositoryImpl() {
        super(Movie.class);
    }

    public Long countAllByUser () {
        try {
            return entityManager.createQuery("SELECT COUNT(e) FROM " + Movie.class.getSimpleName() + " e WHERE e.createdUser  = :email", Long.class)
                    .setParameter("email", securityContext.getUserPrincipal().getName())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public Long countAllPublic() {
        try {
            return entityManager.createQuery("SELECT COUNT(e) FROM " + Movie.class.getSimpleName() + " e WHERE e.isPublic = true", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public List<String> findAllNames() {
        return entityManager.createQuery("SELECT m.name FROM Movie m", String.class)
                .getResultList();
    }

    public Movie findByName(String name) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.name = :name", Movie.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Movie> findAllByUser(Integer limit, Integer page) {
        return entityManager.createQuery("SELECT e FROM " + Movie.class.getSimpleName() + " e WHERE e.createdUser  = :email", Movie.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Movie> findAllByUser() {
        return entityManager.createQuery("SELECT e FROM " + Movie.class.getSimpleName() + " e WHERE e.createdUser  = :email", Movie.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .getResultList();
    }

    public List<Movie> findAllPublic(Integer limit, Integer page) {
        return entityManager.createQuery("SELECT e FROM " + Movie.class.getSimpleName() + " e WHERE e.isPublic = true", Movie.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long countAllByUserFiltered(Map<String, String> columnWithRequest) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(e) FROM " + Movie.class.getSimpleName() + " e WHERE e.createdUser = :email");
        queryBuilder.append(" AND (");

        StringBuilder conditions = new StringBuilder();
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            if (conditions.length() > 0) {
                conditions.append(" OR ");
            }
            conditions.append("LOWER(e.").append(entry.getKey()).append(") LIKE LOWER(:filter").append(entry.getKey()).append(")");
        }
        queryBuilder.append(conditions).append(")");

        String queryString = queryBuilder.toString();
        System.out.println("queryString: " + queryString);

        var query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("email", securityContext.getUserPrincipal().getName());

        // Установите параметры фильтрации
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            String filterValue = "%" + entry.getValue().toLowerCase() + "%"; // Используем значение из columnWithRequest
            query.setParameter("filter" + entry.getKey(), filterValue);
            System.out.println("Setting parameter: filter" + entry.getKey() + " = " + filterValue);
        }

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public Long countAllPublicFiltered(Map<String, String> columnWithRequest) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(e) FROM " + Movie.class.getSimpleName() + " e WHERE e.isPublic = true");
        queryBuilder.append(" AND (");

        StringBuilder conditions = new StringBuilder();
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            if (conditions.length() > 0) {
                conditions.append(" OR ");
            }
            conditions.append("LOWER(e.").append(entry.getKey()).append(") LIKE LOWER(:filter").append(entry.getKey()).append(")");
        }
        queryBuilder.append(conditions).append(")");

        String queryString = queryBuilder.toString();
        System.out.println("queryString: " + queryString);

        var query = entityManager.createQuery(queryString, Long.class);

        // Установите параметры фильтрации
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            String filterValue = "%" + entry.getValue().toLowerCase() + "%"; // Используем значение из columnWithRequest
            query.setParameter("filter" + entry.getKey(), filterValue);
            System.out.println("Setting parameter: filter" + entry.getKey() + " = " + filterValue);
        }

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public List<Movie> findAllByUserFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e FROM " + Movie.class.getSimpleName() + " e WHERE e.createdUser = :email");
        queryBuilder.append(" AND (");

        StringBuilder conditions = new StringBuilder();
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            if (conditions.length() > 0) {
                conditions.append(" OR ");
            }
            conditions.append("LOWER(e.").append(entry.getKey()).append(") LIKE LOWER(:filter").append(entry.getKey()).append(")");
        }
        queryBuilder.append(conditions).append(")");

        String queryString = queryBuilder.toString();
        System.out.println("queryString: " + queryString);

        var query = entityManager.createQuery(queryString, Movie.class);
        query.setParameter("email", securityContext.getUserPrincipal().getName());

        // Установите параметры фильтрации
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            String filterValue = "%" + entry.getValue().toLowerCase() + "%"; // Используем значение из columnWithRequest
            query.setParameter("filter" + entry.getKey(), filterValue);
            System.out.println("Setting parameter: filter" + entry.getKey() + " = " + filterValue);
        }

        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public List<Movie> findAllPublicFiltered(Integer limit, Integer page, Map<String, String> columnWithRequest, String filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e FROM " + Movie.class.getSimpleName() + " e WHERE e.isPublic = true");
        queryBuilder.append(" AND (");

        StringBuilder conditions = new StringBuilder();
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            if (conditions.length() > 0) {
                conditions.append(" OR ");
            }
            conditions.append("LOWER(e.").append(entry.getKey()).append(") LIKE LOWER(:filter").append(entry.getKey()).append(")");
        }
        queryBuilder.append(conditions).append(")");

        String queryString = queryBuilder.toString();
        System.out.println("queryString: " + queryString);

        var query = entityManager.createQuery(queryString, Movie.class);

        // Установите параметры фильтрации
        for (Map.Entry<String, String> entry : columnWithRequest.entrySet()) {
            String filterValue = "%" + entry.getValue().toLowerCase() + "%"; // Используем значение из columnWithRequest
            query.setParameter("filter" + entry.getKey(), filterValue);
            System.out.println("Setting parameter: filter" + entry.getKey() + " = " + filterValue);
        }

        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
