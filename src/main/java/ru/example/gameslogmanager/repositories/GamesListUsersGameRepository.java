package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.GamesListUsersGame;

@Repository
public interface GamesListUsersGameRepository extends JpaRepository<GamesListUsersGame, Integer> {
}