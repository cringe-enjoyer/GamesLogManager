package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Integer> {
}