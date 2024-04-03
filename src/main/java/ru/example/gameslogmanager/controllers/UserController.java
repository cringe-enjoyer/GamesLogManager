package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.FriendStatus;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.FriendsListService;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UserService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")

//TODO: Добавить валидацию
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SteamService steamService;
    private final FriendsListService friendsListService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, SteamService steamService, FriendsListService friendsListService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.steamService = steamService;
        this.friendsListService = friendsListService;
    }

    @GetMapping("/{id}/settings")
    public UserDTO showSettings(@PathVariable("id") int userId) {
        Optional<User> user = userService.getUserById(userId);
        UserDTO userDTO = null;

        if (user.isPresent())
            userDTO = convertToUserDTO(user.get());

        return userDTO;
    }

    @PostMapping("/{id}/settings")
    public ResponseEntity<HttpStatus> saveSettings(@RequestBody UserDTO userDTO, @PathVariable int id) {
        User user = convertToUser(userDTO);
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
        return convertToGamesListDTO(gamesList);
    }

    @GetMapping("/{id}/friends")
    public Set<FriendsListDTO> showFriendsList(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty())
            return null;

        return user.get().getFriendsLists().stream()
                .map((element) -> modelMapper.map(element, FriendsListDTO.class))
                .collect(Collectors.toSet());
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
    public HttpEntity<HttpStatus> decideFriendRequest(@PathVariable int id, @RequestBody FriendsListDTO friendsListDTO) {
        Optional<User> user = userService.getUserById(id);
        Optional<User> friend = userService.getUserByLogin(friendsListDTO.getFriend().getLogin());
        if (user.isPresent() && friend.isPresent())
            friendsListService.sendRequest(user.get(), friend.get(), friendsListDTO.getStatus());
        return new HttpEntity<>(HttpStatus.OK);
    }

    private GamesListDTO convertToGamesListDTO(GamesList gamesList) {
        return modelMapper.map(gamesList, GamesListDTO.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
