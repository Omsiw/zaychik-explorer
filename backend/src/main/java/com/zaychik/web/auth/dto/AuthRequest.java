package com.zaychik.web.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}