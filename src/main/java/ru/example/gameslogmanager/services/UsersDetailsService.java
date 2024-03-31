package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.repositories.UserRepository;
import ru.example.gameslogmanager.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user.get().getUserId(), user.get().getLogin(), user.get().getPassword(),
                List.of(new SimpleGrantedAuthority(user.get().getRole())));//new UserPrincipal(user.get());
    }
}
