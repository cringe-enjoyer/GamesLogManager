package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.GamesList;

@Repository
public interface GamesListRepository extends JpaRepository<GamesList, Integer> {
}