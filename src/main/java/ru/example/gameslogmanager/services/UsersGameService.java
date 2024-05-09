package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.dto.UsersGameDTO;
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

    @Autowired
    public UsersGameService(UsersGameRepository usersGameRepository, SteamService steamService,
                            GamesListService gamesListService, GameService gameService,
                            UsersGameMapper usersGameMapper) {
        this.usersGameRepository = usersGameRepository;
        this.steamService = steamService;
        this.gamesListService = gamesListService;
        this.gameService = gameService;
        this.usersGameMapper = usersGameMapper;
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

        if (list.isEmpty() || game.isEmpty())
            return;

        Optional<UsersGame> usersGame = usersGameRepository.findByGameAndList(game.get(), list.get());

        if (usersGame.isPresent()) {
            UsersGame updatedGame = usersGameMapper.convertToEntity(usersGameDTO, usersGame.get());

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
        return usersGameRepository.findFirst5ByList_UserOrderByDateFinishedDesc(user);
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
}
