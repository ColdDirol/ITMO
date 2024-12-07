package com.backend.backend.security.service;

import com.backend.backend.security.encoder.MD5;
import com.backend.backend.security.model.entity.AdminRequests;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.model.enumeration.UserRoleEnum;
import com.backend.backend.security.repository.AdminRequestsRepository;
import com.backend.backend.security.repository.UserRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepositoryImpl repository;

    @Inject
    AdminRequestsRepository adminRequestsRepository;

    private String salt = "salt";

    @Transactional
    public String register(Users user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
            throw new BadRequestException("Email, password, and role must not be null");
        }

        Users existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new ForbiddenException("User with this email already exists");
        }

        user.setPassword(MD5.encodeToMD5(user.getPassword() + salt));
        if (user.getRole() == UserRoleEnum.ADMIN || user.getRole() == UserRoleEnum.EGOSHIN) {
            if (repository.countAdmins() > 0) {
                AdminRequests request = new AdminRequests();
                request.setUser(user);
                request.setRequestDate(Date.from(Instant.now()));
                adminRequestsRepository.save(request);
                user.setRole(UserRoleEnum.PROCESSING);
                repository.save(user);
                return JwtService.createToken(user);
            }
        }
        repository.save(user);
        return JwtService.createToken(user);
    }

    public String login(Users user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new BadRequestException("Email and password must not be null");
        }
        Users findedUser  = repository.findByEmail(user.getEmail());
        if (findedUser != null && findedUser.getPassword().equals(MD5.encodeToMD5(user.getPassword() + salt))) {
            return JwtService.createToken(findedUser);
        }
        throw new ForbiddenException("Invalid email or password");
    }

    public Users getUserByEmail(String email) {
        Users user = repository.findByEmail(email);
        if (user != null) {
            user.setPassword("");
        }
        return user;
    }
}
