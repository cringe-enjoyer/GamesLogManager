package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.GamesListsResponseDTO;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.exceptions.GamesListNotFoundException;
import ru.example.gameslogmanager.exceptions.UserNotFoundException;
import ru.example.gameslogmanager.mapper.GamesListMapper;
import ru.example.gameslogmanager.mapper.UsersGameMapper;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.UserService;
import ru.example.gameslogmanager.services.UsersGameService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class GamesListController {

    private final GamesListService gamesListService;
    private final UsersGameService usersGameService;
    private final UserService userService;
    private final UsersGameMapper usersGameMapper;
    private final GamesListMapper gamesListMapper;

    @Autowired
    public GamesListController(GamesListService gamesListService, UsersGameService usersGameService,
                               UserService userService, UsersGameMapper usersGameMapper,
                               GamesListMapper gamesListMapper) {
        this.gamesListService = gamesListService;
        this.usersGameService = usersGameService;
        this.userService = userService;
        this.usersGameMapper = usersGameMapper;
        this.gamesListMapper = gamesListMapper;
    }

    @GetMapping()
    public GamesListsResponseDTO getAll(@RequestParam int userid) {
        Optional<User> user = userService.getUserById(userid);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found");

        List<GamesList> allUserLists = gamesListService.getAllByUser(user.get());

        return new GamesListsResponseDTO(allUserLists.stream()
                .map(gamesListMapper::convertToDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public GamesListDTO getList(@PathVariable int id) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isEmpty()) {
            String message = "List not found";
            throw new GamesListNotFoundException(message);
        }

        return gamesListMapper.convertToDTO(list.get());
    }

    @GetMapping("/{id}/count")
    public Long getGamesCountInList(@PathVariable("id") int id) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isEmpty()) {
            String message = "List not found";
            throw new GamesListNotFoundException(message);
        }

        return usersGameService.getCountInList(list.get());
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addList(@RequestBody GamesListDTO gamesListDTO) {
        gamesListService.save(gamesListMapper.convertToEntity(gamesListDTO), gamesListDTO.getUserId());
        return new HttpEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> removeList(@PathVariable("id") int listId) {
        gamesListService.remove(listId);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public HttpEntity<HttpStatus> editList(@PathVariable("id") int listId, @RequestBody GamesListDTO gamesListDTO) {
        gamesListService.edit(gamesListDTO);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/random")
    public UsersGameDTO getRandomGame(@PathVariable int id) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isEmpty()) {
            String message = "List not found";
            throw new GamesListNotFoundException(message);
        }

        UsersGame randomGame = usersGameService.getRandomGame(list.get());
        return usersGameMapper.convertToDTO(randomGame);
    }

    @GetMapping("/{id}/filter")
    public List<UsersGameDTO> getFilteredGames(@PathVariable int id, @RequestParam(required = false) String developer,
                                               @RequestParam(required = false) String genre) {
        Optional<GamesList> list = gamesListService.getById(id);
        if (list.isEmpty()) {
            String message = "List not found";
            throw new GamesListNotFoundException(message);
        }

        return usersGameService.getFilteredGames(list.get(), developer, genre).stream()
                .map(usersGameMapper::convertToDTO).toList();
    }
}
