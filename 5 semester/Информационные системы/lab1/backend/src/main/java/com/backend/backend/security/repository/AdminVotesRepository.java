package com.backend.backend.security.repository;

import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.AdminVotes;
import com.backend.backend.security.model.entity.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AdminVotesRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Transactional
    public void save(AdminVotes votedAdmin) {
        entityManager.persist(votedAdmin);
    }

    public boolean existsVoteForRequest(AdminRequests request) {
        try {
            Long count = entityManager.createQuery(
                            "SELECT COUNT(va) FROM AdminVotes va WHERE va.request = :requestId", Long.class)
                    .setParameter("requestId", request)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existsVoteForRequestAndAdmin(AdminRequests request, Users admin) {
        try {
            Long count = entityManager.createQuery(
                            "SELECT COUNT(va) FROM AdminVotes va WHERE va.request = :requestId AND va.admin = :adminId", Long.class)
                    .setParameter("requestId", request)
                    .setParameter("adminId", admin)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }


    public List<AdminVotes> findVotesByUser(Users user) {
        return entityManager.createQuery(
                        "SELECT v FROM AdminVotes v WHERE v.admin = :user", AdminVotes.class)
                .setParameter("user", user)
                .getResultList();
    }



    public Long countApprovedVotedAdmins(AdminRequests request) {
        try {
            return entityManager.createQuery(
                    "SELECT COUNT(*) FROM AdminVotes va WHERE va.request = :requestId AND va.vote = true"
                    , Long.class)
                        .setParameter("requestId", request)
                        .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public Long countRejectedVotedAdmins(AdminRequests request) {
        try {
            return entityManager.createQuery(
                            "SELECT COUNT(*) FROM AdminVotes va WHERE va.request = :requestId AND va.vote = false"
                            , Long.class)
                    .setParameter("requestId", request)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}
