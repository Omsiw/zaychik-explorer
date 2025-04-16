package com.zaychik.backend.user.model;

import lombok.Builder;

@Builder
public record UserLoginParams(String username, String password) {
    
}
