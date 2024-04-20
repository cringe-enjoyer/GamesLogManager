package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.mapper.*;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.StatisticService;
import ru.example.gameslogmanager.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class StatisticController {

    private final StatisticService statisticService;
    private final UserService userService;
    private final PlatformMapper platformMapper;
    private final UsersGameMapper usersGameMapper;
    private final UsersGoalMapper usersGoalMapper;
    private final GenreMapper genreMapper;

    @Autowired
    public StatisticController(StatisticService statisticService, UserService userService,
                               PlatformMapper platformMapper, UsersGameMapper usersGameMapper,
                               UsersGoalMapper usersGoalMapper, GenreMapper genreMapper) {
        this.statisticService = statisticService;
        this.userService = userService;
        this.platformMapper = platformMapper;
        this.usersGameMapper = usersGameMapper;
        this.usersGoalMapper = usersGoalMapper;
        this.genreMapper = genreMapper;
    }

    //TODO: как бы правильно возвращать эти данные?
    @GetMapping()
    public StatisticResponse getStats(@RequestParam int userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty())
            return null;

        Map<String, Object> userStats = statisticService.getUserStats(user.get());

        StatisticResponse response = new StatisticResponse();
        
        response.setGamesFinished((Integer) userStats.get("games-finished"));
        response.setLists(userStats.containsKey("users-lists") && userStats.get("users-lists") != null ?
                (List<GamesListDTO>) userStats.get("users-lists") : null);

        List<UsersGoal> goals = (List<UsersGoal>) userStats.get("user-goal");
        response.setGoal(goals == null ? null : goals.stream()
                .map(usersGoalMapper::convertToDTO)
                .collect(Collectors.toList()));

        return response;
    }

    @GetMapping("/{id}/platform")
    public GamesByPlatformResponse getPlatformStats(@PathVariable("id") Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return null;
        }

        Map<Platform, List<UsersGame>> userStats = statisticService.getGamesByPlatforms(user.get());
        Map<PlatformDTO, List<UsersGameDTO>> platformStats = new HashMap<>();
        userStats.forEach((platform, usersGames) ->
                platformStats.put(platformMapper.convertToDTO(platform), usersGames.stream()
                        .map(usersGameMapper::convertToDTO)
                        .collect(Collectors.toList())));
        return new GamesByPlatformResponse(platformStats);
    }

    @GetMapping("/{id}/genres")
    public GamesByGenresResponse getGenresStats(@PathVariable("id") Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return null;
        }

        Map<Genre, List<UsersGame>> userStats = statisticService.getGamesByGenres(user.get());

        if (userStats.isEmpty()) return null;

        Map<GenreDTO, List<UsersGameDTO>> genreStats = new HashMap<>();
        userStats.forEach((genre, usersGames) ->
            genreStats.put(genreMapper.convertToDTO(genre),
                    usersGames.stream().map(usersGameMapper::convertToDTO).collect(Collectors.toList())));


        return new GamesByGenresResponse(genreStats);
    }
}
