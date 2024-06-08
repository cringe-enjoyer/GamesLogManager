package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.exceptions.GameNotFoundException;
import ru.example.gameslogmanager.exceptions.GamesListNotFoundException;
import ru.example.gameslogmanager.mapper.UsersGameMapper;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.repositories.UsersGameRepository;
import ru.example.gameslogmanager.dto.GamesPerMonthResponse;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class UsersGameService {

    private final UsersGameRepository usersGameRepository;

    private final SteamService steamService;
    
    private final GamesListService gamesListService;

    private final GameService gameService;

    private final UsersGameMapper usersGameMapper;
    private final DeveloperService developerService;
    private final GenreService genreService;

    @Autowired
    public UsersGameService(UsersGameRepository usersGameRepository, SteamService steamService,
                            GamesListService gamesListService, GameService gameService,
                            UsersGameMapper usersGameMapper, DeveloperService developerService, GenreService genreService) {
        this.usersGameRepository = usersGameRepository;
        this.steamService = steamService;
        this.gamesListService = gamesListService;
        this.gameService = gameService;
        this.usersGameMapper = usersGameMapper;
        this.developerService = developerService;
        this.genreService = genreService;
    }

    public Optional<UsersGame> getUsersGameById(int id) {
        return usersGameRepository.findById(id);
    }

    public Optional<UsersGame> getUsersGameByGameAndList(Game game, GamesList list) {
        return usersGameRepository.findByGameAndList(game, list);
    }

    @Transactional
    public void save(UsersGame game) {
        game.setUpdateDate(LocalDate.now());
        usersGameRepository.save(game);
    }

    @Transactional
    public void saveImported(UsersGame usersGame) {
        usersGame.setUpdateDate(LocalDate.now());
        Optional<UsersGame> oldGame = getUsersGameByGameAndUser(usersGame.getGame(), usersGame.getList().getUser());
        System.out.println(oldGame);
        if (oldGame.isPresent()) {
            System.out.println(oldGame.get());
            oldGame.get().setUserTime(usersGame.getUserTime());
            oldGame.get().setList(usersGame.getList());
            usersGameRepository.save(oldGame.get());
            return;
        }
        usersGameRepository.save(usersGame);
    }

    @Transactional
    public void synchronizeTimeWithSteam(int usersGameId, int steamId) {
        Optional<UsersGame> usersGame = getUsersGameById(usersGameId);
        if (usersGame.isEmpty())
            return;

        usersGame.get().setUpdateDate(LocalDate.now());
        usersGame.get().setUserTime(steamService.getUserTimeInGame(steamId,
                usersGame.get().getList().getUser().getSteamId()));
        usersGameRepository.save(usersGame.get());
    }

    public UsersGame getRandomGame(GamesList list) {
        long count = usersGameRepository.countAllByList(list);
        int randId = (int) (Math.random() * count);
        Page<UsersGame> games = usersGameRepository.findAllByList(PageRequest.of(randId, 1), list);
        UsersGame usersGame = null;
        if (games.hasContent()) {
            usersGame = games.getContent().get(0);
        }
        return usersGame;
    }

    @Transactional
    public void addToList(UsersGame usersGame) {
        usersGame.setUpdateDate(LocalDate.now());
        usersGameRepository.save(usersGame);
    }

    public List<UsersGame> getLastUpdatedGames(GamesList list) {
        return usersGameRepository.findFirstByListOrderByUpdateDateDesc(list);
    }

    @Transactional
    public void remove(UsersGame usersGame) {
        usersGameRepository.delete(usersGame);
    }

    public List<UsersGame> getUsersGamesByPlatformAndUser(Platform platform, User user) {
        return usersGameRepository.findByPlatformAndList_User(platform, user);
    }

    public long getUsersGamesCountByPlatformAndUser(Platform platform, User user) {
        return usersGameRepository.countByPlatformAndList_User(platform, user);
    }

    public List<UsersGame> getUsersGamesByGenre(Genre genre, User user) {
        return usersGameRepository.findByGame_GenresInAndList_User(Set.of(genre), user);
    }

    /**
     * Возвращает игры из указанного списка, добавленные туда после определенной даты
     * @param user пользователь, чьи игры будут возвращены
     * @param date дата добавления в список, после которой будут искаться игры
     * @param listName название списка
     * @return список содержащий игры из списка, добавленные после указанной даты
     */
    public List<UsersGame> getGamesInListAfterDate(User user, LocalDate date, String listName) {
        Optional<GamesList> list = gamesListService.getByUserAndName(user, listName);

        if (list.isPresent())
            return usersGameRepository.findByListAndDateFinishedAfter(list.get(), date);

        return Collections.emptyList();
    }

    @Transactional
    public void updateByListId(UsersGameDTO usersGameDTO, int listId) {
        Optional<GamesList> list = gamesListService.getById(listId);
        Optional<Game> game = gameService.getGameById(usersGameDTO.getGameId());
        
        if (list.isEmpty())
            throw new GamesListNotFoundException("List not found");

        if (game.isEmpty())
            throw new GameNotFoundException("Game not found");

        Optional<UsersGame> usersGame = usersGameRepository.findByGameAndList(game.get(), list.get());

        if (usersGame.isPresent()) {
            UsersGame usersGameNew = usersGameMapper.convertToEntity(usersGameDTO);
            usersGameNew.setId(usersGame.get().getId());
            UsersGame updatedGame = usersGameMapper.convertToEntity(usersGameNew, usersGame.get());
            System.out.println(updatedGame);
            System.out.println(usersGameDTO.getPlatform().getName());
/*            Optional<Platform> platform = platformService.getByName(usersGameDTO.getPlatform().getName());
            platform.ifPresent(updatedGame::setPlatform);*/
            updatedGame.setUpdateDate(LocalDate.now());

            usersGameRepository.save(updatedGame);
        }
    }

    public long getCountInList(GamesList list) {
        return usersGameRepository.countAllByList(list);
    }

    public List<GamesPerMonthResponse> getAllByUserAndBetweenDateFinished(User user, LocalDate start, LocalDate end) {
        return usersGameRepository.findByList_UserAndDateFinishedBetween(user, start, end);
        //return usersGameRepository.findAllByList_UserAndDateFinishedBetweenOrderByDateFinishedDesc(user, start, end);
    }

    public List<UsersGame> get5LastFinishedGames(User user) {
        return usersGameRepository.findFirst5ByList_UserAndDateFinishedNotNullOrderByDateFinishedDesc(user);
    }

    public Optional<UsersGame> getLastFinishedGame(User user) {
        return usersGameRepository.findFirstByList_UserOrderByDateFinishedDesc(user);
    }

    public Long getCountGamesByGenre(Genre genre, User user) {
        return usersGameRepository.countByGame_GenresInAndList_User(List.of(genre), user);
    }

    public Optional<UsersGame> getUsersGameByGameAndUser(Game game, User user) {
        return usersGameRepository.findByGameAndList_User(game, user);
    }

    public List<UsersGame> getFilteredGames(GamesList gamesList, String developer, String genre) {
        Optional<Developer> developerByName = Optional.empty();
        Optional<Genre> genreByName = Optional.empty();
        if (developer != null)
            developerByName = developerService.getDeveloperByName(developer);

        if (genre != null)
            genreByName = genreService.getGenreByName(genre);


        if (developerByName.isPresent() && genreByName.isPresent())
            return usersGameRepository.findByListAndGame_DevelopersInAndGame_GenresIn(gamesList,
                    List.of(developerByName.get()), List.of(genreByName.get()));

        if (developerByName.isPresent())
            return usersGameRepository.findByListAndGame_DevelopersIn(gamesList, List.of(developerByName.get()));

        if (genreByName.isPresent())
            return usersGameRepository.findByListAndGame_GenresIn(gamesList, List.of(genreByName.get()));

        return Collections.emptyList();
    }
}
