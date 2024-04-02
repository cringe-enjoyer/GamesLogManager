package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис обрабатывающий данные для статистики
 */
@Service
@Transactional(readOnly = true)
public class StatisticService {

    private final UsersGameService usersGameService;
    private final GamesListService gamesListService;

    private final String finished = "пройдены";
    private final UsersGoalService usersGoalService;


    @Autowired
    public StatisticService(UsersGameService usersGameService, GamesListService gamesListService,
                            UsersGoalService usersGoalService) {
        this.usersGameService = usersGameService;
        this.gamesListService = gamesListService;
        this.usersGoalService = usersGoalService;
    }

    /**
     * Возвращает набор всех данных по статистике пользователя
     * @param user информация о пользователе
     * @return 
     */
    // TODO: Доработать
    public Map<String, Object> getUserStats(User user) {
        Optional<GamesList> gamesList = gamesListService.findByUserAndName(user, finished);

        Integer gamesFinished = 0;

        if (gamesList.isPresent())
            gamesFinished = gamesList.get().getUsersGames().size();

        Map<String, Object> result = new HashMap<>();
        result.put("games-finished", gamesFinished);

        List<GamesList> userLists = gamesListService.findAllByUser(user);
        result.put("users-lists", userLists);
        //userLists.forEach(list -> result.put(list.getName() + "-list", list.getUsersGames().size()));

        List<UsersGoal> userGoal = usersGoalService.findGoalsByUser(user);

        if (!userGoal.isEmpty())
            result.put("user-goal", userGoal);

        return result;
    }
}
