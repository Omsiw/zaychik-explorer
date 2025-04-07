package com.zaychik.backend.player.model;

import lombok.Builder;

@Builder
public record PlayerLoginParams(String username, String password) {
    
}
