package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.UsersGame;

import java.util.Optional;

@Repository
public interface UsersGameRepository extends JpaRepository<UsersGame, Integer> {
    Optional<UsersGame> findByGameAndList(Game game, GamesList list);
}