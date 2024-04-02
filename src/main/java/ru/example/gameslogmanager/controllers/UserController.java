package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UserService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")

//TODO: Добавить валидацию
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SteamService steamService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, SteamService steamService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.steamService = steamService;
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

    //TODO: Добавить список друзей

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
