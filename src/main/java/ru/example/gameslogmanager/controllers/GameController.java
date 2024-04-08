package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UsersGameService;

import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    private final UsersGameService usersGameService;

    private final ModelMapper modelMapper;
    private final SteamService steamService;

    @Autowired
    public GameController(GameService gameService, UsersGameService usersGameService, ModelMapper modelMapper,
                          SteamService steamService) {
        this.gameService = gameService;
        this.usersGameService = usersGameService;
        this.modelMapper = modelMapper;
        this.steamService = steamService;
    }

    //TODO: Может заменить на GameDTO?
    @GetMapping("/{id}/info")
    public SteamGameInfoDTO getGameInfo(@PathVariable Integer id) {
        Optional<Game> game = gameService.getGameById(id);
        if (game.isEmpty()) {
            SteamGameInfoDTO steamGame = steamService.getGameInfoById(id);
            if (steamGame != null && !steamGame.getName().isEmpty())
                return steamGame;
        }
         
        return null;
    }

    @GetMapping("/{id}")
    public UsersGameDTO getUserGame(@PathVariable("id") Integer gameId, @RequestBody GamesListDTO listDTO) {
        Optional<Game> game = gameService.getGameById(gameId);
        if (game.isEmpty())
            return null;

        Optional<UsersGame> userGame = usersGameService.getUsersGameByGameAndList(game.get(),
                convertToGamesList(listDTO));

        UsersGameDTO response = null;
        if (userGame.isPresent()) {
            response = convertToUsersGameDTO(userGame.get());

            if (game.get().getSteamId() != 0)
                response.setAchievements(steamService.getUserAchievementsForGame(game.get(),
                        convertToUser(listDTO.getUser())));
        }

        return response;
    }

    @PostMapping("/{id}")
    public HttpEntity<HttpStatus> saveUserGameData(@RequestBody UsersGameDTO gameDTO, @PathVariable int id) {
/*        Optional<Game> game = gameService.getGameById(id);
        gamesListService.getByUserAndName()
        if (game.isPresent()) {

            Optional<UsersGame> usersGameInBD = usersGameService.getUsersGameByGameAndList(game, )
        }*/
        usersGameService.save(convertToUsersGame(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/time")
    public HttpEntity<HttpStatus> synchronizeUserTimeWithSteam(@PathVariable("id") int gameId,
                                                               @RequestBody UsersGameDTO usersGameDTO) {
        Optional<Game> game = gameService.getGameById(gameId);
        if (game.isEmpty())
            return null;

        usersGameService.synchronizeTimeWithSteam(convertToUsersGame(usersGameDTO), game.get().getSteamId());
        /*long time = steamService.getUserTimeInGame(game.get().getSteamId(), usersGameDTO.getList().getUser().getSteamId());
        usersGameDTO.setUserTime(time);
        usersGameService.save(convertToUsersGame(usersGameDTO));*/
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/post")
    public HttpEntity<HttpStatus> postReview(@PathVariable("id") int gameId, @RequestBody UsersGameDTO gameDTO) {
        usersGameService.save(convertToUsersGame(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> deleteUsersGame(@PathVariable("id") int gameId, @RequestBody UsersGameDTO gameDTO) {
        usersGameService.remove(convertToUsersGame(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public HttpEntity<HttpStatus> createGame(@RequestBody @Valid GameDTO gameDTO) {
        gameService.save(convertToGame(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
    }

    private Game convertToGame(GameDTO gameDTO) {
        return modelMapper.map(gameDTO, Game.class);
    }

    private UsersGameDTO convertToUsersGameDTO(UsersGame userGame) {
        return modelMapper.map(userGame, UsersGameDTO.class);
    }

    private UsersGame convertToUsersGame(UsersGameDTO gameDTO) {
        return modelMapper.map(gameDTO, UsersGame.class);
    }

    private GamesList convertToGamesList(GamesListDTO listDTO) {
        return modelMapper.map(listDTO, GamesList.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
