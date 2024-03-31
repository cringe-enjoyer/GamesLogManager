package ru.example.gameslogmanager.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JwtToPrincipalConverter {
    /**
     * Преобразует JWT токен в {@link UserPrincipal}
     * @param jwt верифицированный токен
     * @return UserPrincipal
     */
    public UserPrincipal convert(DecodedJWT jwt) {
        return new UserPrincipal(Integer.getInteger(jwt.getSubject()), jwt.getClaim("login").asString(),
                jwt.getClaim("password").asString(), extractAuthoritiesFromClaim(jwt));
    }

    /**
     * Преобразует роль в список SimpleGrantedAuthority
     * @param jwt jwt токен
     * @return список SimpleGrantedAuthority
     */
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim role = jwt.getClaim("role");
        if (role.isNull() || role.isMissing())
            return Collections.emptyList();
        return role.asList(SimpleGrantedAuthority.class);
    }
}
