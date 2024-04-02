package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.repositories.UsersGameRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersGameService {

    private final UsersGameRepository usersGameRepository;
    private final SteamService steamService;

    @Autowired
    public UsersGameService(UsersGameRepository usersGameRepository, SteamService steamService) {
        this.usersGameRepository = usersGameRepository;
        this.steamService = steamService;
    }

    public Optional<UsersGame> getUsersGameById(int id) {
        return usersGameRepository.findById(id);
    }

    public Optional<UsersGame> getUsersGameByGameAndList(Game game, GamesList list) {
        return usersGameRepository.findByGameAndList(game, list);
    }

    @Transactional
    public void save(UsersGame game) {
        usersGameRepository.save(game);
    }

    @Transactional
    public void synchronizeTimeWithSteam(UsersGame usersGame, int steamId) {
       usersGame.setUserTime(steamService.getUserTimeInGame(steamId, usersGame.getList().getUser().getSteamId()));
       usersGameRepository.save(usersGame);
    }

    public UsersGame findRandomGame(GamesList list) {
        long count = usersGameRepository.countAllByList(list);
        int randId = (int) (Math.random() * count);
        Page<UsersGame> games = usersGameRepository.findAllByList(PageRequest.of(randId, 1), list);
        UsersGame usersGame = null;
        if (games.hasContent()) {
            usersGame = games.getContent().get(0);
        }
        return usersGame;
    }
}
