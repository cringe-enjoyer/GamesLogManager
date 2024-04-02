package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.StatisticResponse;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.dto.UsersGoalDTO;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;
import ru.example.gameslogmanager.services.StatisticService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class StatisticController {

    private final StatisticService statisticService;
    private final ModelMapper modelMapper;

    @Autowired
    public StatisticController(StatisticService statisticService, ModelMapper modelMapper) {
        this.statisticService = statisticService;
        this.modelMapper = modelMapper;
    }

    //TODO: как бы правильно возвращать эти данные?
    @GetMapping()
    public StatisticResponse getStats(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        Map<String, Object> userStats = statisticService.getUserStats(user);

        StatisticResponse response = new StatisticResponse();
        
        response.setGamesFinished((Integer) userStats.get("games-finished"));
        response.setLists(userStats.containsKey("users-lists") && userStats.get("users-lists") != null ?
                (List<GamesListDTO>) userStats.get("users-lists") : null);

        List<UsersGoal> goals = (List<UsersGoal>) userStats.get("user-goal");
        response.setGoal(goals == null ? null : goals.stream()
                .map((element) -> modelMapper.map(element, UsersGoalDTO.class))
                .collect(Collectors.toList()));

        return response;
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
