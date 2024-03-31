package ru.example.gameslogmanager.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс хранящий параметры необходимые для JWT токена
 */
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
