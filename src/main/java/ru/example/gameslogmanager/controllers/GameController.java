package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.mapper.GameMapper;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.SteamService;

import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    private final SteamService steamService;

    private final GameMapper gameMapper;

    @Autowired
    public GameController(GameService gameService, SteamService steamService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.steamService = steamService;
        this.gameMapper = gameMapper;
    }

    @GetMapping("/{id}")
    public GameDTO getGame(@PathVariable Integer id) {
        Optional<Game> game = gameService.getGameById(id);
        if (game.isEmpty()) {
            SteamGameInfoDTO steamGame = steamService.getGameInfoById(id);
            if (steamGame != null && !steamGame.getName().isEmpty()) {
                steamService.saveSteamGame(steamGame.getSteamAppid());
                game = gameService.getGameBySteamId(steamGame.getSteamAppid());
            }
        }

        return gameMapper.convertToDTO(game.get());
    }

    // TODO: Протестировать
    @PostMapping("/create")
    public HttpEntity<HttpStatus> createGame(@RequestBody @Valid GameDTO gameDTO) {
        gameService.save(gameMapper.convertToEntity(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }
}
