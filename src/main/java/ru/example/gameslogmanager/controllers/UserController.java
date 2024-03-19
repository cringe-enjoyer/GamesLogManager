package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")

//TODO: Добавить валидацию
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/settings")
    public UserDTO showSettings(@RequestBody String login) {
        Optional<User> user = userService.getUserByLogin(login);
        UserDTO userDTO = null;

        if (user.isPresent())
            userDTO = convertToUserDTO(user.get());

        return userDTO;
    }

    @PostMapping("/settings")
    public ResponseEntity<HttpStatus> saveSettings(@RequestBody UserDTO userDTO) {
        userService.save(convertToUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
