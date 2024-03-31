package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.dto.LoginResponse;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.repositories.UserRepository;
import ru.example.gameslogmanager.security.JWTIssuer;
import ru.example.gameslogmanager.security.UserPrincipal;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTIssuer jwtIssuer,
                               AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    public LoginResponse login(String login, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtIssuer.issue(principal.getId(), principal.getLogin(), roles);

        return new LoginResponse(token);
    }
}
