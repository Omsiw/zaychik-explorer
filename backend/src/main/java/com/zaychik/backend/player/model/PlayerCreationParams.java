package com.zaychik.backend.player.model;

import lombok.Builder;

@Builder
public record PlayerCreationParams(String username, String password, String email) {
    
}
