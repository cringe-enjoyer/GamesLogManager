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

    public Optional<GamesList> getById(int id) {
        return gamesListRepository.findById(id);
    }

    public List<GamesList> getAllByUser(User user) {
        return gamesListRepository.findAllByUser(user);
    }

    public Long getGamesCountInListById(int id) {
        return gamesListRepository.countById(id);
    }

    @Transactional(readOnly = false)
    public void save(GamesList gamesList) {
        gamesListRepository.save(gamesList);
    }

    @Transactional
    public void remove(int id) {
        gamesListRepository.deleteById(id);
    }

    public Optional<GamesList> getByUserAndName(User user, String name) {
        return gamesListRepository.findByUserAndName(user, name);
    }
}
