package com.backend.backend.security.provider;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.backend.backend.security.model.entity.Users;
import com.backend.backend.security.model.enumeration.UserRoleEnum;
import com.backend.backend.security.repository.UserRepositoryImpl;
import com.backend.backend.security.service.JwtService;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;

@Provider
public class JwtProvider implements ContainerRequestFilter {

    @Inject
    UserRepositoryImpl userRepository; // Внедряем репозиторий

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.startsWith("/auth") || path.startsWith("/v1/movie/all-public") || path.startsWith("/v1/movie/count/all-public")) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();

        String email = null;
        String role = null;
        try {
            if (JwtService.isTokenExpired(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
            email = JwtService.extractEmail(token);
            role = JwtService.extractRole(token);
        } catch (JWTDecodeException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        Users user = userRepository.findByEmail(email);
        if (user == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String finalEmail = email;
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return finalEmail;
            }

            @Override
            public boolean isUserInRole(String role) {
                return role.equals(role);
            }

            @Override
            public boolean isSecure() {
                return requestContext.getUriInfo().getBaseUri().getScheme().equals("https");
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
