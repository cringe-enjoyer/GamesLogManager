package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    List<User> findByNicknameStartsWithIgnoreCase(String nickname);
}
