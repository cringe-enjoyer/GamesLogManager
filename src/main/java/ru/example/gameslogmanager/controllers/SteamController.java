package ru.example.gameslogmanager.controllers;

import com.lukaspradel.steamapi.data.json.ownedgames.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesRequest;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.exceptions.UserNotFoundException;
import ru.example.gameslogmanager.mapper.UsersGameMapper;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UserService;
import ru.example.gameslogmanager.services.UsersGameService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/steam")
public class SteamController {

    private final SteamService steamService;
    private final UserService userService;
    private final UsersGameMapper usersGameMapper;
    private final UsersGameService usersGameService;

    @Autowired
    public SteamController(SteamService steamService, UserService userService, UsersGameMapper usersGameMapper, UsersGameService usersGameService) {
        this.steamService = steamService;
        this.userService = userService;
        this.usersGameMapper = usersGameMapper;
        this.usersGameService = usersGameService;
    }

    @GetMapping("/import/{id}")
    public List<Game> importSteamLibrary(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()){
            String message = "Пользователь не найден";
            throw new UserNotFoundException(message);
        }

        return steamService.getUserSteamGames(user.get().getSteamId());

/*        GamesList gamesList = steamService.importSteamLibrary(user.get(),
                request.getListName() == null || request.getListName().isEmpty() ? null : request.getListName());*/
        //return gamesListMapper.convertToDTO(gamesList); //Надо подумать
    }

    @PostMapping("/steamGames")
    public List<UsersGameDTO> importSteamLibrary(@RequestBody GamesRequest games) {

        List<UsersGame> usersGames = steamService.importUserSteamGames(games.getGames());

        return usersGames.stream().map(usersGameMapper::convertToDTO).collect(Collectors.toList());

/*        GamesList gamesList = steamService.importSteamLibrary(user.get(),
                request.getListName() == null || request.getListName().isEmpty() ? null : request.getListName());*/
        //return gamesListMapper.convertToDTO(gamesList); //Надо подумать
    }

    @PostMapping("/import/{id}")
    public HttpEntity<HttpStatus> saveImportedGames(@PathVariable int id, @RequestBody List<UsersGameDTO> usersGamesDTO) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            String message = "Пользователь не найден";
            throw new UserNotFoundException(message);
        }

        //TODO: Нужна проверка если уже существует такой usersGame
        List<UsersGame> usersGames = usersGamesDTO.stream().map(usersGameMapper::convertToEntity).toList();
        usersGames.forEach(usersGameService::saveImported);

        return new HttpEntity<>(HttpStatus.OK);
    }
}
