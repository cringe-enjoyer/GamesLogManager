package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.*;
import ru.example.gameslogmanager.exceptions.UserNotFoundException;
import ru.example.gameslogmanager.mapper.GameMapper;
import ru.example.gameslogmanager.mapper.UsersGoalMapper;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.StatisticService;
import ru.example.gameslogmanager.services.UserService;
import ru.example.gameslogmanager.dto.GamesPerMonthResponse;
import ru.example.gameslogmanager.services.UsersGameService;
import ru.example.gameslogmanager.utils.DefaultLists;

import java.time.LocalDate;
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
    private final UsersGoalMapper usersGoalMapper;
    private final GameService gameService;
    private final GameMapper gameMapper;
    private final UsersGameService usersGameService;

    @Autowired
    public StatisticController(StatisticService statisticService, UserService userService,
                               UsersGoalMapper usersGoalMapper, GameService gameService,
                               GameMapper gameMapper, UsersGameService usersGameService) {
        this.statisticService = statisticService;
        this.userService = userService;
        this.usersGoalMapper = usersGoalMapper;
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.usersGameService = usersGameService;
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
    public Map<String, Long> getPlatformStats(@PathVariable("id") Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }

/*        Map<Platform, List<UsersGame>> userStats = statisticService.getGamesByPlatforms(user.get());
        Map<String, List<UsersGameDTO>> platformStats = new HashMap<>();
        userStats.forEach((platform, usersGames) ->
                platformStats.put(platform.getName(), usersGames.stream()
                        .map(usersGameMapper::convertToDTO)
                        .collect(Collectors.toList())));
        return new GamesByPlatformResponse(platformStats);*/

        Map<Platform, Long> userStats = statisticService.getGamesCountByPlatforms(user.get());
        Map<String, Long> platformStats = new HashMap<>();
        userStats.forEach((platform, count) -> platformStats.put(platform.getName(), count));
        return platformStats;
    }

    @GetMapping("/{id}/genres")
    public Map<String, Long> getGenresStats(@PathVariable("id") Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }
/*
        Map<Genre, List<UsersGame>> userStats = statisticService.getGamesByGenres(user.get());

        if (userStats.isEmpty()) return Map.of();

        Map<GenreDTO, List<UsersGameDTO>> genreStats = new HashMap<>();
        userStats.forEach((genre, usersGames) ->
            genreStats.put(genreMapper.convertToDTO(genre),
                    usersGames.stream().map(usersGameMapper::convertToDTO).collect(Collectors.toList())));


        return new GamesByGenresResponse(genreStats);*/

        return statisticService.getCountGamesByGenres(user.get());
    }

    @GetMapping("/{id}/month")
    public List<GamesPerMonthResponse> getGamesPerMonth(@PathVariable("id") int userId,
                                                        @RequestParam(required = false) Integer year) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }
        if (year == null || year == 0)
            year = LocalDate.now().getYear();

        return statisticService.getFinishedGamesPerMonth(user.get(), year);
    }

    @GetMapping("/{id}/in-progress")
    public List<GameDTO> getCurrentGames(@PathVariable("id") int userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }

        return gameService.getGamesInList(user.get(), DefaultLists.INPROGRES.getRuValue()).stream()
                .map(gameMapper::convertToDTO)
                .toList();
    }

    @GetMapping("/{id}/last-finished")
    public List<GameDTO> getLastFinishedGames(@PathVariable("id") int userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }

        return usersGameService.get5LastFinishedGames(user.get()).stream()
                .map(UsersGame::getGame)
                .map(gameMapper::convertToDTO)
                .toList();
    }
}
