package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}