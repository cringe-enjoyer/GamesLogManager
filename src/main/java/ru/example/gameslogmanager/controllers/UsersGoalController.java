package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.UsersGoalDTO;
import ru.example.gameslogmanager.mapper.UsersGoalMapper;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;
import ru.example.gameslogmanager.services.StatisticService;
import ru.example.gameslogmanager.services.UserService;
import ru.example.gameslogmanager.services.UsersGoalService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/goal")
public class UsersGoalController {

    private final UsersGoalService usersGoalService;
    private final UserService userService;
    private final StatisticService statisticService;
    private final UsersGoalMapper usersGoalMapper;

    @Autowired
    public UsersGoalController(UsersGoalService usersGoalService, UserService userService,
                               StatisticService statisticService, UsersGoalMapper usersGoalMapper) {
        this.usersGoalService = usersGoalService;
        this.userService = userService;
        this.statisticService = statisticService;
        this.usersGoalMapper = usersGoalMapper;
    }

    @GetMapping
    public UsersGoalDTO getUserGoal(@RequestParam Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return null;
        }

        Optional<UsersGoal> userGoal = usersGoalService.getUserGoalByUser(user.get());
        if (userGoal.isEmpty()) {
            return null;
        }

        userGoal.get().setDone(statisticService.getFinishedGamesCountInCurrentYear(user.get()));
        return usersGoalMapper.convertToDTO(userGoal.get());
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addUserGoal(@RequestBody UsersGoalDTO usersGoalDTO) {
        if (Objects.isNull(usersGoalDTO))
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);

        usersGoalService.save(usersGoalMapper.convertToEntity(usersGoalDTO));
        return new HttpEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> deleteUserGoal(@PathVariable Integer id) {
        Optional<UsersGoal> goal = usersGoalService.getUserGoalById(id);
        if (goal.isEmpty())
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);

        usersGoalService.delete(goal.get());
        return new HttpEntity<>(HttpStatus.OK);
    }
}
