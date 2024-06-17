package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.repositories.GamesListRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GamesListService {

    private final GamesListRepository gamesListRepository;
    private final UserService userService;

    @Autowired
    public GamesListService(GamesListRepository gamesListRepository, UserService userService) {
        this.gamesListRepository = gamesListRepository;
        this.userService = userService;
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

    @Transactional(readOnly = false)
    public void save(GamesList gamesList, int userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty())
            return;

        gamesList.setUser(user.get());
        gamesListRepository.save(gamesList);
    }

    @Transactional
    public void remove(int id) {
        gamesListRepository.deleteById(id);
    }

    public Optional<GamesList> getByUserAndName(User user, String name) {
        return gamesListRepository.findByUserAndName(user, name);
    }

    @Transactional
    public void edit(GamesListDTO gamesList) {
        Optional<GamesList> list = gamesListRepository.findById(gamesList.getId());
        if (list.isEmpty())
            return;

        list.get().setName(gamesList.getName());
        gamesListRepository.save(list.get());
    }
}
