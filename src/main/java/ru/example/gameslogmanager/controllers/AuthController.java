package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.LoginRequest;
import ru.example.gameslogmanager.dto.LoginResponse;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.mapper.UserMapper;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.RegistrationService;
import ru.example.gameslogmanager.utils.DefaultLists;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final UserMapper userMapper;
    private final GamesListService gamesListService;

    @Autowired
    public AuthController(RegistrationService registrationService, UserMapper userMapper, GamesListService gamesListService) {
        this.registrationService = registrationService;
        this.userMapper = userMapper;
        this.gamesListService = gamesListService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        User savedUser = registrationService.register(user);
        if (savedUser != null) {
            for (DefaultLists list : DefaultLists.values()) {
                GamesList gamesList = new GamesList();
                gamesList.setName(list.getRuValue());
                gamesList.setUser(savedUser);
                gamesList.setDefault(true);

                gamesListService.save(gamesList);
            }
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return registrationService.login(request.getLogin(), request.getPassword());
    }
}
