package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.repositories.UsersGameRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class UsersGameService {

    private final UsersGameRepository usersGameRepository;

    private final SteamService steamService;
    
    private final GamesListService gamesListService;

    @Autowired
    public UsersGameService(UsersGameRepository usersGameRepository, SteamService steamService, GamesListService gamesListService) {
        this.usersGameRepository = usersGameRepository;
        this.steamService = steamService;
        this.gamesListService = gamesListService;
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
    public void synchronizeTimeWithSteam(UsersGame usersGame, int steamId) {
        usersGame.setUpdateDate(LocalDate.now());
       usersGame.setUserTime(steamService.getUserTimeInGame(steamId, usersGame.getList().getUser().getSteamId()));
       usersGameRepository.save(usersGame);
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
        usersGame.setDateAdded(LocalDate.now());
        usersGame.setUpdateDate(LocalDate.now());
        usersGameRepository.save(usersGame);
    }

    public List<UsersGame> getLastUpdatedGames(GamesList list) {
        return usersGameRepository.findFirst3ByOrderByUpdateDateDesc(list);
    }

    @Transactional
    public void remove(UsersGame usersGame) {
        usersGameRepository.delete(usersGame);
    }

    public List<UsersGame> getUsersGamesByPlatformAndUser(Platform platform, User user) {
        return usersGameRepository.findByPlatformAndList_User(platform, user);
    }

    public List<UsersGame> getUsersGamesByGenre(Genre genre, User user) {
        return usersGameRepository.findByGame_GenresAndList_User(genre, user);
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
            return usersGameRepository.findByListAndDateAddedAfter(list.get(), date);

        return Collections.emptyList();
    }
}
