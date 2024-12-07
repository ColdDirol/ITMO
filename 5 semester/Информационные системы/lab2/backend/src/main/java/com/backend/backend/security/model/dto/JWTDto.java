package com.backend.backend.security.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JWTDto {
    private String token;
}
