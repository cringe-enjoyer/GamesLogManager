package ru.example.gameslogmanager.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    //private final User user;
    private final Integer id;
    private final String login;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String login, String password, Collection<? extends GrantedAuthority> authorities) {
        //this.user = user;
        this.id = id;
        this.password = password;
        this.authorities = authorities;
        this.login = login;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
        //return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return password;
        //return user.getPassword();
    }

    @Override
    public String getUsername() {
        return login;
        //return user.getLogin();
    }

    // Аккаунт не просрочен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Пароль не просрочен
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Аккаунт работает
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно, чтобы получать данные аутентифицированного пользователя
    /*public User getUser() {
        return user;
    }*/

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }
}
