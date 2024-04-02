package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.GameService;
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
    public GameController(GameService gameService, UsersGameService usersGameService,
                          ModelMapper modelMapper, SteamService steamService) {
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
    public HttpEntity<HttpStatus> saveUserGameData(@RequestBody UsersGameDTO gameDTO, @PathVariable String id) {
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

    // TODO: Это надо перенести в список
    @GetMapping("/search")
    public GamesResponse findGames(@RequestParam(required = false) String developer,
                                   @RequestParam(required = false) List<String> genres,
                                   @RequestParam(required = false) String publisher) {
        Map<String, Object> filters = new HashMap<>();
        if (developer != null && !developer.isEmpty())
            filters.put("dev", developer);
        if (publisher != null && !publisher.isEmpty())
            filters.put("publisher", publisher);
        if (genres != null && !genres.isEmpty())
            filters.put("genres", genres);

        Set<Game> games = gameService.getGamesByFilters(filters);

        return new GamesResponse(games.stream().map((element) -> modelMapper.map(element, GameDTO.class)).toList());
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
