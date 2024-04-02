package ru.example.gameslogmanager.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.Genre;
import ru.example.gameslogmanager.models.Publisher;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByTitle(String title);

    Optional<Game> findBySteamId(Integer steamId);

    List<Game> findByTitleStartsWith(String title, Pageable pageable);

    //TODO: Проверить запросы
    List<Game> findDistinctByGenresAllIgnoreCase(Set<Genre> genres);

    Set<Game> findDistinctByDevelopersAndPublishers(Developer developers, Publisher publishers);

    Set<Game> findDistinctByDevelopersAndPublishersAndGenresIn(Developer developers, Publisher publishers, Collection<Genre> genres);

    Set<Game> findDistinctByDevelopersAndGenresIn(Developer developers, Collection<Genre> genres);

    Set<Game> findDistinctByPublishersAndGenresIn(Publisher publishers, Collection<Genre> genres);

    Set<Game> findDistinctByDevelopers(Developer developers);

    Set<Game> findDistinctByPublishers(Publisher publishers);

    Set<Game> findDistinctByGenres(Set<Genre> genres);
}