package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.exceptions.UsersGameNotFoundException;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.*;
import ru.example.gameslogmanager.mapper.UsersGameMapper;

import java.util.Optional;

@RestController
@RequestMapping("user-game")
public class UsersGameController {

    private final UsersGameService usersGameService;
    private final UsersGameMapper usersGameMapper;
    private final GameService gameService;
    private final GamesListService gamesListService;
    private final SteamService steamService;

    @Autowired
    public UsersGameController(UsersGameService usersGameService, UsersGameMapper usersGameMapper,
                               GameService gameService, GamesListService gamesListService, SteamService steamService) {
        this.usersGameService = usersGameService;
        this.usersGameMapper = usersGameMapper;
        this.gameService = gameService;
        this.gamesListService = gamesListService;
        this.steamService = steamService;
    }

    @GetMapping()
    public UsersGameDTO getUserGameByGameAndList(@RequestParam Integer gameId, @RequestParam Integer listId) {
        Optional<Game> game = gameService.getGameById(gameId);
        if (game.isEmpty())
            return null;

        Optional<GamesList> list = gamesListService.getById(listId);
        if (list.isEmpty())
            return null;

        Optional<UsersGame> userGame = usersGameService.getUsersGameByGameAndList(game.get(), list.get());

        UsersGameDTO response = null;
        if (userGame.isPresent()) {
            response = usersGameMapper.convertToDTO(userGame.get());

            if (game.get().getSteamId() != 0 && userGame.get().getFromSteam())
                response.setAchievements(steamService.getUserAchievementsForGame(game.get(), list.get().getUser()));
        }

        return response;
    }

    @GetMapping("/{id}")
    public UsersGameDTO getUserGameById(@PathVariable("id") Integer userGameId) {
        Optional<UsersGame> game = usersGameService.getUsersGameById(userGameId);
        if (game.isEmpty()) {
            String message = "Game with id " + userGameId + " not found";
            throw new UsersGameNotFoundException(message);
        }

        UsersGameDTO response = usersGameMapper.convertToDTO(game.get());

        if (game.get().getGame().getSteamId() != null && game.get().getFromSteam())
            response.setAchievements(steamService.getUserAchievementsForGame(game.get().getGame(),
                    game.get().getList().getUser()));

        return response;
    }

/*    @GetMapping()
    public UsersGameDTO getUserGameByGameAndUser(@RequestParam Integer gameId, @RequestParam Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User with id not found";
            throw new UserNotFoundException(message);
        }

        Optional<Game> game = gameService.getGameById(gameId);

        if (game.isEmpty()) {
            String message = "Game with not found";
            throw new GameNotFoundException(message);
        }

        Optional<UsersGame> usersGame = usersGameService.getUsersGameByGameAndUser(game.get(), user.get());

        if (usersGame.isEmpty()) {
            String message = "Users game not found";
            throw new UsersGameNotFoundException(message);
        }

        UsersGameDTO response = usersGameMapper.convertToDTO(usersGame.get());

        if (game.get().getSteamId() != null && usersGame.get().getFromSteam())
            response.setAchievements(steamService.getUserAchievementsForGame(game.get(), user.get()));

        return response;
    }*/

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> deleteUsersGame(@PathVariable("id") int usersGameId) {
        Optional<UsersGame> usersGame = usersGameService.getUsersGameById(usersGameId);
        if (usersGame.isEmpty())
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);

        usersGameService.remove(usersGame.get());
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> saveUserGameData(@RequestBody UsersGameDTO gameDTO) {
        Optional<Game> game = gameService.getGameById(gameDTO.getGameId());
        Optional<GamesList> list = gamesListService.getById(gameDTO.getListId());
        UsersGame usersGame = null;
        if (game.isPresent() && list.isPresent()) {
            Optional<UsersGame> usersGameInBD = usersGameService.getUsersGameByGameAndList(game.get(), list.get());
            if (usersGameInBD.isPresent()) {
                usersGame = usersGameMapper.convertToEntity(gameDTO, usersGameInBD.get());

                usersGameService.save(usersGame);
                return new HttpEntity<>(HttpStatus.OK);
            }
        }

        usersGame = usersGameMapper.convertToEntity(gameDTO);
/*        usersGame.setPlatform(platformService.getByName(gameDTO.getPlatform().getName()).orElse(null));
        usersGame.setGame(gameService.getGameById(gameDTO.getGameId()).orElseThrow());
        usersGame.setList(gamesListService.getById(gameDTO.getListId()).orElseThrow());*/

        usersGameService.save(usersGame);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PatchMapping()
    public HttpEntity<HttpStatus> updateUsersGameData(@RequestBody UsersGameDTO gameDTO) {
        usersGameService.updateByListId(gameDTO, gameDTO.getListId());
        return new HttpEntity<>(HttpStatus.OK);
    }


    @PostMapping("/{id}/post")
    public HttpEntity<HttpStatus> postReview(@PathVariable("id") int gameId, @RequestBody UsersGameDTO gameDTO) {
        Optional<UsersGame> usersGame = usersGameService.getUsersGameById(gameId);
        if (usersGame.isEmpty())
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);

        UsersGame updatedUsersGame = usersGame.get();
        updatedUsersGame.setUserReview(gameDTO.getUserReview());
        updatedUsersGame.setPublicReview(gameDTO.isPublicReview());

        usersGameService.save(updatedUsersGame);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/time")
    public HttpEntity<HttpStatus> synchronizeUserTimeWithSteam(@PathVariable("id") int usersGameId,
                                                               @RequestBody UsersGameDTO usersGameDTO) {
        Optional<Game> game = gameService.getGameById(usersGameDTO.getGameId());

        if (game.isEmpty() && !usersGameDTO.isFromSteam())
            return null;

        usersGameService.synchronizeTimeWithSteam(usersGameId, game.get().getSteamId());
        /*long time = steamService.getUserTimeInGame(game.get().getSteamId(), usersGameDTO.getList().getUser().getSteamId());
        usersGameDTO.setUserTime(time);
        usersGameService.save(convertToUsersGame(usersGameDTO));*/
        return new HttpEntity<>(HttpStatus.OK);
    }
}
