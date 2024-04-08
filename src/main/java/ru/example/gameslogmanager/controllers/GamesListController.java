package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.GamesListsResponseDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.UsersGameService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class GamesListController {

    private final GamesListService gamesListService;
    private final ModelMapper modelMapper;
    private final UsersGameService usersGameService;

    @Autowired
    public GamesListController(GamesListService gamesListService, ModelMapper modelMapper, UsersGameService usersGameService) {
        this.gamesListService = gamesListService;
        this.modelMapper = modelMapper;
        this.usersGameService = usersGameService;
    }

    @GetMapping()
    public GamesListsResponseDTO getAll(@RequestBody UserDTO userDTO) {
        List<GamesList> allUserLists = gamesListService.getAllByUser(convertToUser(userDTO));

        return new GamesListsResponseDTO(allUserLists.stream()
                .map((element) -> modelMapper.map(element, GamesListDTO.class))
                .toList());
    }

    @GetMapping("/{id}")
    public GamesListDTO getList(@PathVariable int id) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isPresent())
            return convertGamesListDTO(list.get());
        return null;
    }

    @GetMapping("/{id}/count")
    public Long getGamesCountInList(@PathVariable("id") int id) {
        return gamesListService.getGamesCountInListById(id);
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addList(@RequestBody GamesListDTO gamesListDTO) {
        gamesListService.save(convertToGamesList(gamesListDTO));
        return new HttpEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping()
    public HttpEntity<HttpStatus> removeList(@RequestBody GamesListDTO gamesListDTO) {
        Optional<GamesList> list = gamesListService.getByUserAndName(convertToUser(gamesListDTO.getUser()),
                gamesListDTO.getName());

        if (!list.isPresent())
            return null;

        gamesListService.remove(list.get().getId());

        return new HttpEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> removeList(@PathVariable("id") int listId) {
        gamesListService.remove(listId);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/random")
    public UsersGameDTO getRandomGame(@PathVariable int id) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isEmpty())
            return null;

        UsersGame randomGame = usersGameService.getRandomGame(list.get());
        return convertToUsersGameDTO(randomGame);
    }

    private UsersGameDTO convertToUsersGameDTO(UsersGame randomGame) {
        return modelMapper.map(randomGame, UsersGameDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private GamesListDTO convertGamesListDTO(GamesList gamesList) {
        return modelMapper.map(gamesList, GamesListDTO.class);
    }

    private GamesList convertToGamesList(GamesListDTO gamesListDTO) {
        return modelMapper.map(gamesListDTO, GamesList.class);
    }
}
