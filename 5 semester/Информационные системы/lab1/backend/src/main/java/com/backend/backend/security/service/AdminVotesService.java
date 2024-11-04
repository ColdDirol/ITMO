package com.backend.backend.security.service;

import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.AdminVotes;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.repository.AdminRequestsRepository;
import com.backend.backend.security.repository.AdminVotesRepository;
import com.backend.backend.security.repository.UserRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class AdminVotesService {

    @Inject
    private AdminVotesRepository adminVotesRepository;

    @Inject
    private AdminRequestsRepository adminRequestsRepository;

    @Inject AdminRequestsService adminRequestsService;

    @Inject
    UserRepositoryImpl userRepository;

    @Inject
    private SecurityContext securityContext;

    public List<AdminRequests> getPendingRequestsForCurrentUser() {
        Users currentUser = userRepository.findByEmail(securityContext.getUserPrincipal().getName());
        return adminRequestsRepository.findRequestsNotVotedByUser(currentUser);
    }

    @Transactional
    public void approve(Long id) {
        AdminRequests request = adminRequestsRepository.findById(id);
        Users currentUser = userRepository.findByEmail(securityContext.getUserPrincipal().getName());

        if (adminVotesRepository.existsVoteForRequestAndAdmin(request, currentUser)) {
            throw new BadRequestException("You have already voted on this request.");
        }

        AdminVotes vote = new AdminVotes();
        vote.setRequest(request);
        vote.setAdmin(currentUser);
        vote.setVote(true);
        adminVotesRepository.save(vote);
        adminRequestsService.getRequestStatusByUserEmail(request.getUser().getEmail());
    }

    @Transactional
    public void reject(Long id) {
        AdminRequests request = adminRequestsRepository.findById(id);
        Users currentUser = userRepository.findByEmail(securityContext.getUserPrincipal().getName());

        if (adminVotesRepository.existsVoteForRequestAndAdmin(request, currentUser)) {
            throw new BadRequestException("You have already voted on this request.");
        }

        AdminVotes vote = new AdminVotes();
        vote.setRequest(request);
        vote.setAdmin(currentUser);
        vote.setVote(false);
        adminVotesRepository.save(vote);
        adminRequestsService.getRequestStatusByUserEmail(request.getUser().getEmail());
    }

}
