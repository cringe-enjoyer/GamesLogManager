package ru.example.gameslogmanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Класс для создания JWT токена
 */
@Component
public class JWTIssuer {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    /**
     * Шифрует id, логин и роль пользователя в JWT токен
     *
     * @param userId id пользователя
     * @param login логин пользователя
     * @param roles роль пользователя
     * @return сгенерированный JWT токен
     */
    public String issue(int userId, String login, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("login", login)
                .withClaim("role", roles)
                .sign(Algorithm.HMAC256(jwtSecretKey));
    }
}
