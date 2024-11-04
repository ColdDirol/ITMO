package com.backend.backend.security.service;

import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.model.enumeration.AdminRequestStatusEnum;
import com.backend.backend.security.model.enumeration.UserRoleEnum;
import com.backend.backend.security.repository.AdminRequestsRepository;
import com.backend.backend.security.repository.UserRepositoryImpl;
import com.backend.backend.security.repository.AdminVotesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class AdminRequestsService {

    @Inject
    private AdminRequestsRepository adminRequestsRepository;

    @Inject
    private AdminVotesRepository adminVotesRepository;

    @Inject
    private UserRepositoryImpl userRepository;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public void save(AdminRequests request) {
        adminRequestsRepository.save(request);
    }

    public void findById(Long id) {
        AdminRequests adminRequests = adminRequestsRepository.findById(id);
    }

    public List<AdminRequests> getPendingRequests() {
        return adminRequestsRepository.findPendingRequests();
    }

    public List<AdminRequests> getPendingRequestsForCurrentUser() {
        List<AdminRequests> adminRequestsList = adminRequestsRepository.findRequestsNotVotedByUser(
                userRepository.findByEmail(securityContext.getUserPrincipal().getName())
        );

        adminRequestsList.forEach(request -> {
            if (request.getUser() != null) {
                request.getUser().setPassword("");
            }
        });

        return adminRequestsList;
    }


    @Transactional
    public void approveRequest(Long requestId) {
        AdminRequests request = adminRequestsRepository.findById(requestId);
        if (request != null) {
            request.setIsApproved(true);
            adminRequestsRepository.update(request);
            Users user = request.getUser();
            user.setRole(UserRoleEnum.ADMIN);
            userRepository.update(user);
        }
    }

    @Transactional
    public void rejectRequest(Long requestId) {
        AdminRequests request = adminRequestsRepository.findById(requestId);
        if (request != null) {
            request.setIsApproved(true);
            adminRequestsRepository.update(request);
            Users user = request.getUser();
            user.setRole(UserRoleEnum.ADMIN);
            userRepository.update(user);
        }
    }

    public AdminRequestStatusEnum getRequestStatus() {
        return getRequestStatusByUserEmail(securityContext.getUserPrincipal().getName());
    }

    public AdminRequestStatusEnum getRequestStatusByUserEmail(String email) {
        AdminRequests request =
                adminRequestsRepository.findByUserId(
                        userRepository.findByEmail(email).getId()
                );
        if (request.getIsApproved() == null) {
            Long adminCount = userRepository.countAdmins();
            Long approvedAdminsCount = adminVotesRepository.countApprovedVotedAdmins(request);
            if (adminCount == approvedAdminsCount) {
                approveRequest(request.getId());
                return AdminRequestStatusEnum.APPROVED;
            }
            Long rejectedAdminsCount = adminVotesRepository.countRejectedVotedAdmins(request);
            if (adminCount == approvedAdminsCount + rejectedAdminsCount) {
                rejectRequest(request.getId());
                return AdminRequestStatusEnum.REJECTED;
            }
            return AdminRequestStatusEnum.PROCESSING;
        }
        if (request.getIsApproved()) {
            return AdminRequestStatusEnum.APPROVED;
        } else {
            return AdminRequestStatusEnum.REJECTED;
        }
    }
}