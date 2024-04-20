package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.GamesResponse;
import ru.example.gameslogmanager.dto.UserListResponse;
import ru.example.gameslogmanager.mapper.FriendMapper;
import ru.example.gameslogmanager.mapper.GameMapper;
import ru.example.gameslogmanager.mapper.UserMapper;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final GameService gameService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final GameMapper gameMapper;
    private final FriendMapper friendMapper;

    @Autowired
    public SearchController(GameService gameService, UserService userService, UserMapper userMapper,
                            GameMapper gameMapper, FriendMapper friendMapper) {
        this.gameService = gameService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.gameMapper = gameMapper;
        this.friendMapper = friendMapper;
    }

    @GetMapping("/game")
    public GamesResponse findGames(@RequestParam String request, @RequestParam(required = false) String developer,
                                   @RequestParam(required = false) List<String> genres,
                                   @RequestParam(required = false) String publisher) {

        Map<String, Object> filters = new HashMap<>();
        if (developer != null && !developer.isEmpty())
            filters.put("dev", developer);
        if (publisher != null && !publisher.isEmpty())
            filters.put("publisher", publisher);
        if (genres != null && !genres.isEmpty())
            filters.put("genres", genres);

        Set<Game> games = gameService.getGamesByFilters(request, filters);

        return new GamesResponse(games.stream().map(gameMapper::convertToDTO).toList());

    }

    @GetMapping("/user")
    public UserListResponse findUsers(@RequestParam String request) {
        return new UserListResponse(
                userService.getUsersByNickname(request).stream()
                        .map(friendMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
    }
}
