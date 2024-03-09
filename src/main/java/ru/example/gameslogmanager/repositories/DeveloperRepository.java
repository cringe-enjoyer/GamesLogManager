package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Developer;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findByName(String name);
}