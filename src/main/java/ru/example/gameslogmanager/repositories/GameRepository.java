package ru.example.gameslogmanager.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.Genre;
import ru.example.gameslogmanager.models.Publisher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByTitle(String title);

    Optional<Game> findBySteamId(Integer steamId);

    List<Game> findByTitleStartsWith(String title, Pageable pageable);

    //TODO: Проверить запросы
    List<Game> findDistinctByGenres(Set<Genre> genres);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndDevelopersAndPublishers(String title, Set<Developer> developers,
                                                                                  Set<Publisher> publishers);

    Set<Game> findDistinctByTitleStartsWithIgnoreCaseAndDevelopersAndPublishersAndGenres(String title,
                                                                                         Set<Developer> developers,
                                                                                         Set<Publisher> publishers,
                                                                                         Set<Genre> genres);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndDevelopersAndGenres(String title, Set<Developer> developers,
                                                                              Set<Genre> genres);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndPublishersAndGenres(String title, Set<Publisher> publishers,
                                                                              Set<Genre> genres);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndDevelopers(String title, Set<Developer> developers);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndPublishers(String title, Set<Publisher> publishers);

    Set<Game> findDistinctByTitleStartingWithIgnoreCaseAndGenres(String title, Set<Genre> genres);

    Set<Game> findByTitleStartsWithIgnoreCase(String title);
}