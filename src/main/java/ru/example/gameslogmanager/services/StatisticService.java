package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.utils.DefaultLists;

import java.time.LocalDate;
import java.time.Month;
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

    private final UsersGoalService usersGoalService;

    private final PlatformService platformService;

    private final GenreService genreService;


    @Autowired
    public StatisticService(UsersGameService usersGameService, GamesListService gamesListService,
                            UsersGoalService usersGoalService, PlatformService platformService, GenreService genreService) {
        this.usersGameService = usersGameService;
        this.gamesListService = gamesListService;
        this.usersGoalService = usersGoalService;
        this.platformService = platformService;
        this.genreService = genreService;
    }

    /**
     * Возвращает набор всех данных по статистике пользователя
     * @param user информация о пользователе
     * @return 
     */
    // TODO: Доработать
    public Map<String, Object> getUserStats(User user) {
        Optional<GamesList> gamesList = gamesListService.getByUserAndName(user, DefaultLists.FINISHED.getRuValue());

        Integer gamesFinished = 0;

        if (gamesList.isPresent())
            gamesFinished = gamesList.get().getUsersGames().size();

        Map<String, Object> result = new HashMap<>();
        result.put("games-finished", gamesFinished);

        List<GamesList> userLists = gamesListService.getAllByUser(user);
        result.put("users-lists", userLists);
        //userLists.forEach(list -> result.put(list.getName() + "-list", list.getUsersGames().size()));

        List<UsersGoal> userGoal = usersGoalService.getGoalsByUser(user);

        if (!userGoal.isEmpty())
            result.put("user-goal", userGoal);

        return result;
    }


    public Map<Platform, List<UsersGame>> getGamesByPlatforms(User user) {
        Map<Platform, List<UsersGame>> gamesByPlatforms = new HashMap<>();
        List<Platform> allPlatforms = platformService.getAllPlatforms();

        for (Platform platform : allPlatforms) {
            //TODO: Проверить этот запрос
            List<UsersGame> usersGamesByPlatform = usersGameService.getUsersGamesByPlatformAndUser(platform, user);
            gamesByPlatforms.put(platform, usersGamesByPlatform);
        }

        return gamesByPlatforms;
    }

    /**
     * Возвращает Map содержащую список игр соответствующий жанру
     * @param user пользователь чьи игры будут возвращены
     * @return Map с ключами жанрами и списками игр
     */
    public Map<Genre, List<UsersGame>> getGamesByGenres(User user) {
        Map<Genre, List<UsersGame>> gamesByGenres = new HashMap<>();
        List<Genre> genres = genreService.getAllGenres();
        genres.forEach(genre -> gamesByGenres.put(genre, usersGameService.getUsersGamesByGenre(genre, user)));
        return gamesByGenres;
    }

    /**
     * Возвращает количество пройденных игр за текущий год
     * @param user пользователь чьи игры будут возвращены
     * @return количество пройденных игр за текущий год
     */
    public int getFinishedGamesCountInCurrentYear(User user) {
        return usersGameService.getGamesInListAfterDate(user, LocalDate.of(
                LocalDate.now().getYear(), Month.JANUARY, 1), DefaultLists.FINISHED.getRuValue()).size();
    }
}
