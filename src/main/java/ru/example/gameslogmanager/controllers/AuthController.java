package ru.example.gameslogmanager.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.LoginRequest;
import ru.example.gameslogmanager.dto.LoginResponse;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.RegistrationService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;


    @Autowired
    public AuthController(RegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid UserDTO userDTO) {
        //TODO: Добавить создание 3 списков
        User user = convertToUser(userDTO);
        registrationService.register(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return registrationService.login(request.getLogin(), request.getPassword());
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
