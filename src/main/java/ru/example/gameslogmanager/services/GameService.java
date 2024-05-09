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
    private final GamesListService gamesListService;

    @Autowired
    public GameService(GameRepository gameRepository, DeveloperService developerService,
                       PublisherService publisherService, GenreService genreService, GamesListService gamesListService) {
        this.gameRepository = gameRepository;
        this.developerService = developerService;
        this.publisherService = publisherService;
        this.genreService = genreService;
        this.gamesListService = gamesListService;
    }

    public Optional<Game> getGameById(int id) {
        return gameRepository.findById(id);
    }

    public Optional<Game> getGameByTitle(String title) {
        return gameRepository.findByTitle(title);
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

    public List<Game> getGamesByGenres(Set<Genre> genres) {
        return gameRepository.findDistinctByGenres(genres);
    }

    //TODO: Разобраться надо ли это тут или в список перенести
    public Set<Game> getGamesByFilters(String request, Map<String, Object> filters) {
        Developer developer = null;
        Publisher publisher = null;
        Set<Genre> genres = null;
        if (filters.containsKey("dev") && filters.get("dev") != null)
            developer = developerService.getDeveloperByName(String.valueOf(filters.get("dev")))
                    .orElse(null);

        if (filters.containsKey("publisher") && filters.get("publisher") != null)
            publisher = publisherService.getPublisherByName(String.valueOf(filters.get("publisher")))
                    .orElse(null);

        if (filters.containsKey("genres") && filters.get("genres") != null)
            genres = genreService.getAllGenresByNames((List<String>) filters.get("genres"));

        if (developer != null && publisher != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByTitleStartsWithIgnoreCaseAndDevelopersAndPublishersAndGenres(request,
                    Set.of(developer), Set.of(publisher), genres);

        if (developer != null && publisher != null)
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndDevelopersAndPublishers(request,
                    Set.of(developer), Set.of(publisher));

        if (developer != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndDevelopersAndGenres(request,
                    Set.of(developer), genres);

        if (publisher != null && genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndPublishersAndGenres(request,
                    Set.of(publisher), genres);

        if (developer != null)
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndDevelopers(request, Set.of(developer));

        if (publisher != null)
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndPublishers(request, Set.of(publisher));

        if (genres != null && !genres.isEmpty())
            return gameRepository.findDistinctByTitleStartingWithIgnoreCaseAndGenres(request, genres);

        return gameRepository.findByTitleStartsWithIgnoreCase(request);
    }

    public List<Game> getGamesInList(User user, String name) {
        Optional<GamesList> list = gamesListService.getByUserAndName(user, name);
        return list.map(gamesList -> gamesList.getUsersGames().stream().map(UsersGame::getGame).toList()).orElse(Collections.emptyList());
    }
}
