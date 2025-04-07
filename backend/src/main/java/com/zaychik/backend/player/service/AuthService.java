package com.zaychik.backend.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zaychik.backend.player.entity.Player;
import com.zaychik.backend.player.factory.PlayerFactory;
import com.zaychik.backend.player.jwt.JwtUtil;
import com.zaychik.backend.player.model.PlayerCreationParams;
import com.zaychik.backend.player.repo.PlayerRepository;
import com.zaychik.web.auth.dto.RegisterRequest;

@Service
public class AuthService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (playerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        PlayerCreationParams params2 = new PlayerCreationParams(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getEmail());
        Player player = PlayerFactory.creatPlayer(params2);
        // можешь задать роль: player.setRole("ROLE_PLAYER");

        playerRepository.save(player);

        // Вернуть токен сразу после регистрации:
        return jwtUtil.generateToken(player);
    }
}
