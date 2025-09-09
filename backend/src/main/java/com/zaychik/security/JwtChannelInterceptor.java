package com.zaychik.security;

import com.zaychik.backend.user.service.UserDetailsServiceImpl;
import com.zaychik.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j // Обязательно для логгирования
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);
                
                try {
                    String username = jwtUtil.extractUsername(jwt);
                    if (username != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(jwt, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            accessor.setUser(authToken);
                            log.info("✅ Пользователь '{}' успешно аутентифицирован для WebSocket.", username);
                        }
                    }
                } catch (ExpiredJwtException e) {
                    log.warn("❌ Ошибка аутентификации WebSocket: токен истек - {}", e.getMessage());
                } catch (SignatureException e) {
                    log.warn("❌ Ошибка аутентификации WebSocket: неверная подпись токена - {}", e.getMessage());
                } catch (MalformedJwtException e) {
                    log.warn("❌ Ошибка аутентификации WebSocket: некорректный формат токена - {}", e.getMessage());
                } catch (Exception e) {
                    log.error("❌ Непредвиденная ошибка при аутентификации WebSocket: {}", e.getMessage());
                }
            } else {
                 log.warn("⚠️ Попытка WebSocket подключения без Bearer токена.");
            }

        }
        return message;
    }
}