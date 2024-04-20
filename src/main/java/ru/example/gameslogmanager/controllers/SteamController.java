package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.SteamImportRequest;
import ru.example.gameslogmanager.mapper.GamesListMapper;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/steam")
public class SteamController {

    private final SteamService steamService;
    private final UserService userService;
    private final GamesListMapper gamesListMapper;

    @Autowired
    public SteamController(SteamService steamService, UserService userService, GamesListMapper gamesListMapper) {
        this.steamService = steamService;
        this.userService = userService;
        this.gamesListMapper = gamesListMapper;
    }

    @GetMapping("/import")
    public GamesListDTO importSteamLibrary(@RequestBody SteamImportRequest request) {
        Optional<User> user = userService.getUserByLogin(request.getUserDTO().getLogin());
        if (user.isEmpty())
            return null;
        GamesList gamesList = steamService.importSteamLibrary(user.get(),
                request.getListName() == null || request.getListName().isEmpty() ? null : request.getListName());
        return gamesListMapper.convertToDTO(gamesList); //Надо подумать
    }
}
