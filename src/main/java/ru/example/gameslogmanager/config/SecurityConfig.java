package ru.example.gameslogmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.example.gameslogmanager.security.JwtAuthenticationFilter;
import ru.example.gameslogmanager.services.UsersDetailsService;

//TODO: Доделать авторизацию

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UsersDetailsService usersDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(UsersDetailsService usersDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.usersDetailsService = usersDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Конфигурация для Boot 3
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                // TODO: Разобраться с csrf
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/**") // Распространяет правило на всё приложение
                // Настройка доступа к страницам
                .authorizeHttpRequests((requests) -> requests
                                //.requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/error", "auth/registration", "auth/login").permitAll()
                                .anyRequest().authenticated()
                                //.anyRequest().hasAnyRole("USER", "ADMIN") // Доступ ко всем страницам для роли USER и ADMIN
                )
                // Аутентификация
                .formLogin(AbstractHttpConfigurer::disable
                        /*.loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/user/settings", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()*/

                )
                // Разлогиневание
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder encoder) {
/*        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(usersDetailsService).passwordEncoder(getPasswordEncoder());
        return builder.build();*/

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(usersDetailsService);
        authenticationProvider.setPasswordEncoder(encoder);

        return new ProviderManager(authenticationProvider);
    }

}
