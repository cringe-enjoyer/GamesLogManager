package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamesListRepository extends JpaRepository<GamesList, Integer> {
    List<GamesList> findAllByUser(User user);

    Long countById(int id);

    Optional<GamesList> findByUserAndName(User user, String name);
}