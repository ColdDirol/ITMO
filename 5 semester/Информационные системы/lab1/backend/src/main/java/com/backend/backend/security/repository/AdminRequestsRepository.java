package com.backend.backend.security.repository;

import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.AdminVotes;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.model.enumeration.UserRoleEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AdminRequestsRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Inject
    private AdminVotesRepository adminVotesRepository;

    @Transactional
    public void save(AdminRequests request) {
        entityManager.persist(request);
    }

    public AdminRequests findById(Long id) {
        return entityManager.find(AdminRequests.class, id);
    }

    public AdminRequests findByUserId(Long userId) {
        return entityManager.createQuery(
                        "SELECT r FROM " + AdminRequests.class.getSimpleName() + " r WHERE r.user.id = :userId"
                , AdminRequests.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
    }

    public List<AdminRequests> findPendingRequests() {
        return entityManager.createQuery("SELECT r FROM " + AdminRequests.class.getSimpleName() + " r WHERE r.isApproved is null", AdminRequests.class).getResultList();
    }

    @Transactional
    public AdminRequests update(AdminRequests adminRequest) {
        return entityManager.merge(adminRequest);
    }

    public List<AdminRequests> findRequestsNotVotedByUser(Users user) {
        // Получаем все голоса текущего пользователя
        List<AdminVotes> votes = adminVotesRepository.findVotesByUser(user);

        // Получаем идентификаторы заявок, на которые пользователь проголосовал
        List<Long> votedRequestIds = votes.stream()
                .map(vote -> vote.getRequest().getId())
                .toList();

        // Получаем все непрошедшие заявки
        List<AdminRequests> allPendingRequests = findPendingRequests();

        // Исключаем заявки, на которые уже голосовал пользователь
        return allPendingRequests.stream()
                .filter(request -> !votedRequestIds.contains(request.getId()))
                .toList();
    }



    public void approveRequest(Long requestId) {
        AdminRequests request = entityManager.find(AdminRequests.class, requestId);
        if (request != null) {
            request.setIsApproved(true);
            entityManager.merge(request);
            Users user = request.getUser();
            user.setRole(UserRoleEnum.ADMIN);
            entityManager.merge(user);
        }
    }

    public Boolean getRequestStatus(Long requestId) {
        return entityManager.find(AdminRequests.class, requestId).getIsApproved();
    }
}