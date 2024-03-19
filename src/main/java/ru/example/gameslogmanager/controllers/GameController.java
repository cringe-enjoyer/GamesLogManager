package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.SteamGameInfoDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.UsersGameService;
import ru.example.gameslogmanager.utils.SteamGamesRequests;

import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    private final UsersGameService usersGameService;

    private final GamesListService gamesListService;

    private final ModelMapper modelMapper;

    @Autowired
    public GameController(GameService gameService, UsersGameService usersGameService, GamesListService gamesListService,
                          ModelMapper modelMapper) {
        this.gameService = gameService;
        this.usersGameService = usersGameService;
        this.gamesListService = gamesListService;
        this.modelMapper = modelMapper;
    }

    //TODO: Может заменить на GameDTO?
    @GetMapping("/{id}/info")
    public SteamGameInfoDTO getGameInfo(@PathVariable Integer id) {
        Optional<Game> game = gameService.findGameById(id);
        if (game.isEmpty()) {
            SteamGameInfoDTO steamGame = SteamGamesRequests.getGameInfoById(id);
            if (steamGame != null && !steamGame.getName().isEmpty())
                return steamGame;
        }
         
        return null;
    }

    @GetMapping("/{id}")
    public UsersGameDTO getUserGame(@PathVariable("id") Integer gameId, @RequestBody GamesListDTO listDTO) {
        Optional<Game> game = gameService.findGameById(gameId);
        if (game.isEmpty())
            return null;

        Optional<UsersGame> userGame = usersGameService.getUsersGameByGameAndList(game.get(),
                convertToGamesList(listDTO));

        return convertToUsersGameDTO(userGame.orElse(null));
    }

    @PostMapping("/{id}")
    public HttpEntity<HttpStatus> saveUserGameData(@RequestBody UsersGameDTO gameDTO, @PathVariable String id) {
        usersGameService.save(convertToUsersGame(gameDTO));
        return new HttpEntity<>(HttpStatus.OK);
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
}
