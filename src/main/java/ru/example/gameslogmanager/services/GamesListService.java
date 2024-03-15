package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.repositories.GamesListRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GamesListService {

    private final GamesListRepository gamesListRepository;

    @Autowired
    public GamesListService(GamesListRepository gamesListRepository) {
        this.gamesListRepository = gamesListRepository;
    }

    public Optional<GamesList> findById(int id) {
        return gamesListRepository.findById(id);
    }

    public List<GamesList> findAllByUser(User user) {
        return gamesListRepository.findAllByUser(user);
    }

    public Long getGamesCountInListById(int id) {
        return gamesListRepository.countById(id);
    }

    @Transactional(readOnly = false)
    public void save(GamesList gamesList) {
        gamesListRepository.save(gamesList);
    }
}
