package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersGoalRepository extends JpaRepository<UsersGoal, Integer> {
    Optional<UsersGoal> findByUser(User user);

    List<UsersGoal> findAllByUser(User user);
}