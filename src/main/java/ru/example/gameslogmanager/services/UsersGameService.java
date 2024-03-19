package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UsersGameService(UsersGameRepository usersGameRepository) {
        this.usersGameRepository = usersGameRepository;
    }

    public Optional<UsersGame> getUsersGameById(int id) {
        return usersGameRepository.findById(id);
    }

    public Optional<UsersGame> getUsersGameByGameAndList(Game game, GamesList list) {
        return usersGameRepository.findByGameAndList(game, list);
    }

    public void save(UsersGame game) {
        usersGameRepository.save(game);
    }

}
