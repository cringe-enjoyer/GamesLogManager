package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.repositories.DeveloperRepository;
import ru.example.gameslogmanager.repositories.GameRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;

    private final DeveloperRepository developerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, DeveloperRepository developerRepository) {
        this.gameRepository = gameRepository;
        this.developerRepository = developerRepository;
    }

    public Optional<Game> findGameById(int id) {
        return gameRepository.findById(id);
    }

    public Optional<Game> findGameByTitle(String title) {
        return gameRepository.findByTitle(title);
    }

    public List<Game> findGameByGenre(String genre) {
        return gameRepository.findAllByGenre(genre);
    }

    public List<Game> findAllByDeveloperName(String developer) {
        Optional<Developer> foundDev = developerRepository.findByName(developer);
        if (foundDev.isPresent())
            return gameRepository.findAllByDeveloper(foundDev.get());

        return Collections.emptyList();
    }
}
