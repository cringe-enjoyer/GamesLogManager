package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.mapper.GamesListMapper;
import ru.example.gameslogmanager.mapper.UserMapper;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.*;
import ru.example.gameslogmanager.utils.DefaultLists;

import java.util.*;

@RestController
@RequestMapping("/user")

//TODO: Добавить валидацию
public class UserController {
    private final UserService userService;
    private final SteamService steamService;
    private final FriendsListService friendsListService;
    private final UsersGameService usersGameService;
    private final GamesListService gamesListService;
    private final GamesListMapper gamesListMapper;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, SteamService steamService, FriendsListService friendsListService,
                          UsersGameService usersGameService, GamesListService gamesListService,
                          GamesListMapper gamesListMapper, UserMapper userMapper) {
        this.userService = userService;
        this.steamService = steamService;
        this.friendsListService = friendsListService;
        this.usersGameService = usersGameService;
        this.gamesListService = gamesListService;
        this.gamesListMapper = gamesListMapper;
        this.userMapper = userMapper;
    }

    //TODO: Ограничить доступ другим пользователям
    @GetMapping("/{id}/settings")
    public UserDTO showSettings(@PathVariable("id") int userId) {
        Optional<User> user = userService.getUserById(userId);
        UserDTO userDTO = null;

        if (user.isPresent())
            userDTO = userMapper.convertToDTO(user.get());

        return userDTO;
    }

    @PostMapping("/{id}/settings")
    public ResponseEntity<HttpStatus> saveSettings(@RequestBody UserDTO userDTO, @PathVariable int id) {
        User user = userMapper.convertToEntity(userDTO);
        user.setUserId(id);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/settings/import_steam")
    public GamesListDTO importSteamLibrary(@PathVariable("id") int userId,
                                           @RequestBody(required = false) Map<String, String> listName) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty())
            return null;
        GamesList gamesList = steamService.importSteamLibrary(user.get(),
                listName == null || listName.isEmpty() || !listName.containsKey("list") ? null : listName.get("list"));
        return gamesListMapper.convertToDTO(gamesList);
    }

    @PostMapping("/{id}")
    public HttpEntity<HttpStatus> sendFriendRequest(@PathVariable("id") int friendId,
                                                    @RequestBody @Valid UserDTO userDTO) {
        Optional<User> user = userService.getUserByLogin(userDTO.getLogin());
        Optional<User> friend = userService.getUserById(friendId);
        if (user.isEmpty() || friend.isEmpty()) {
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);
        }

        friendsListService.sendRequest(user.get(), friend.get(), FriendStatus.SENT);
        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/friends")
    public HttpEntity<HttpStatus> decideFriendRequest(@PathVariable int id,
                                                      @RequestBody FriendsListDTO friendsListDTO) {
        Optional<User> user = userService.getUserById(id);
        Optional<User> friend = userService.getUserById(friendsListDTO.getFriendId());
        if (user.isPresent() && friend.isPresent())
            friendsListService.sendRequest(user.get(), friend.get(), friendsListDTO.getStatus());
        return new HttpEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/last-finished-games")
    public List<UsersGame> getLastUpdatedGames(@PathVariable("id") int userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty())
            return Collections.emptyList();

        Optional<GamesList> listFinished = gamesListService.getByUserAndName(user.get(),
                DefaultLists.FINISHED.getRuValue());

        if (listFinished.isEmpty())
            return Collections.emptyList();

        return usersGameService.getLastUpdatedGames(listFinished.get());
    }
}
