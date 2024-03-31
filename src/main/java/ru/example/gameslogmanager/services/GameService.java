package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.repositories.GameRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;

    private final DeveloperService developerService;
    private final PublisherService publisherService;
    private final GenreService genreService;

    @Autowired
    public GameService(GameRepository gameRepository, DeveloperService developerService, PublisherService publisherService, GenreService genreService) {
        this.gameRepository = gameRepository;
        this.developerService = developerService;
        this.publisherService = publisherService;
        this.genreService = genreService;
    }

    public Optional<Game> getGameById(int id) {
        return gameRepository.findById(id);
    }

    public Optional<Game> getGameByTitle(String title) {
        return gameRepository.findByTitle(title);
    }

    public List<Game> getGameByGenre(Set<Genre> genres) {
        return gameRepository.findDistinctByGenresAllIgnoreCase(genres);
    }

    public List<Game> getAllByDeveloperName(String developer) {
        Optional<Developer> foundDev = developerService.getDeveloperByName(developer);
        if (foundDev.isPresent())
            return foundDev.get().getGames();
            //return gameRepository.findAllByDevelopers(foundDev.get());

        return Collections.emptyList();
    }

    public Optional<Game> getGameBySteamId(int steamId) {
        return gameRepository.findBySteamId(steamId);
    }

    @Transactional
    public void save(Game game) {
        gameRepository.save(game);
    }

    public List<Game> getGamesByGenre(Set<Genre> genres) {
        return gameRepository.findDistinctByGenresAllIgnoreCase(genres);
    }

    //TODO: Разобраться надо ли это тут или в список перенести
    public Set<Game> getGamesByFilters(Map<String, Object> filters) {
        Developer developer = null;
        Publisher publisher = null;
        Set<Genre> genres = null;
        if (filters.containsKey("dev") && filters.get("dev") != null)
            developer = developerService.getDeveloperByName(String.valueOf(filters.get("dev")))
                    .orElse(null);

        if (filters.containsKey("publisher") && filters.get("publisher") != null)
            publisher = publisherService.findPublisherByName(String.valueOf(filters.get("publisher")))
                    .orElse(null);

        if (filters.containsKey("genres") && filters.get("genres") != null)
            genres = genreService.getAllGenresByNames((List<String>) filters.get("genres"));

        if (developer != null && publisher != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByDevelopersAndPublishersAndGenresIn(developer, publisher, genres);

        if (developer != null && publisher != null)
            return gameRepository.findDistinctByDevelopersAndPublishers(developer, publisher);

        if (developer != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByDevelopersAndGenresIn(developer, genres);

        if (publisher != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByPublishersAndGenresIn(publisher, genres);

        if (developer != null)
            return gameRepository.findDistinctByDevelopers(developer);

        if (publisher != null)
            return gameRepository.findDistinctByPublishers(publisher);

        if (genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByGenres(genres);

        return null; // gameRepository.find
    }
}
