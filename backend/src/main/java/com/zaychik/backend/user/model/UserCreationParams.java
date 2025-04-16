package com.zaychik.backend.user.model;

import lombok.Builder;

@Builder
public record UserCreationParams(String username, String password, String email) {
    
}
