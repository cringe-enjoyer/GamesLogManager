package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.UsersGame;

@Repository
public interface UsersGameRepository extends JpaRepository<UsersGame, Integer> {
}