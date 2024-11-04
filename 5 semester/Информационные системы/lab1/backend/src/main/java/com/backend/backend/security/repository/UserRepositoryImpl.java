package com.backend.backend.security.repository;

import com.backend.backend.model.entities.Movie;
import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.model.enumeration.UserRoleEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepositoryImpl {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Transactional
    public void save(Users user) {
        entityManager.persist(user);
    }

    public Users findByEmail(String email) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE u.email = :email", Users.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void update(Users user) {
        entityManager.merge(user);
    }

    public Long countAdmins() {
        try {
            return entityManager.createQuery("SELECT COUNT(u) FROM Users u WHERE u.role = :admin", Long.class)
                    .setParameter("admin", UserRoleEnum.ADMIN)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

}
