package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.exceptions.GameNotCreated;
import ru.example.gameslogmanager.exceptions.GameNotFoundException;
import ru.example.gameslogmanager.mapper.GameMapper;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.GameService;

import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;


    private final GameMapper gameMapper;

    @Autowired
    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping("/{id}")
    public GameDTO getGame(@PathVariable Integer id) {
        Optional<Game> game = gameService.getGameById(id);
        /*if (game.isEmpty()) {
            SteamGameInfoDTO steamGame = steamService.getGameInfoById(id);
            if (steamGame != null && !steamGame.getName().isEmpty()) {
                steamService.saveSteamGame(steamGame.getSteamAppid());
                game = gameService.getGameBySteamId(steamGame.getSteamAppid());
            }
        }*/
        if (game.isEmpty())
            throw new GameNotFoundException("Game with id: " + id + " not found");

        return gameMapper.convertToDTO(game.get());
    }

    // TODO: Протестировать и добавить создание UserGame привязанного к пользователю
    @PostMapping("/create")
    public HttpEntity<HttpStatus> createGame(@RequestBody @Valid GameDTO gameDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }

            throw new GameNotCreated(errors.toString());
        }
        gameService.save(gameMapper.convertToEntity(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }
}
