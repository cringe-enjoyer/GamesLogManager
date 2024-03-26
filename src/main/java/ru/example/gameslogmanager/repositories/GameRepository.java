package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.models.Game;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByTitle(String title);

    List<Game> findAllByGenre(String genre);

    Optional<Game> findBySteamId(Integer steamId);
}