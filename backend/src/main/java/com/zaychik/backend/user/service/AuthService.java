package com.zaychik.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.factory.UserFactory;
import com.zaychik.backend.user.model.UserCreationParams;
import com.zaychik.backend.user.repo.UserRepository;
import com.zaychik.security.util.JwtUtil;
import com.zaychik.web.auth.dto.RegisterRequest;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        UserCreationParams params2 = new UserCreationParams(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getEmail());
        User user = UserFactory.createUser(params2);
        // можешь задать роль: user.setRole("ROLE_user");

        userRepository.save(user);

        // Вернуть токен сразу после регистрации:
        return jwtUtil.generateToken(user);
    }
}
