package ru.example.gameslogmanager.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.example.gameslogmanager.dto.GamesPerMonthResponse;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.utils.DefaultLists;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

class StatisticServiceTest {
    @Mock
    UsersGameService usersGameService;
    @Mock
    GamesListService gamesListService;
    @Mock
    UsersGoalService usersGoalService;
    @Mock
    PlatformService platformService;
    @Mock
    GenreService genreService;
    @InjectMocks
    StatisticService statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserStats() {
        GamesList returnedGamesList = new GamesList();
        returnedGamesList.setName(DefaultLists.FINISHED.getRuValue());
        returnedGamesList.setUser(new User("login", "password", "ioannis_callehyz0@uncle.mm",
                "123548", "nickname"));
        returnedGamesList.setUsersGames(List.of(new UsersGame()));
        UsersGoal returnedUsersGoal = new UsersGoal();
        returnedUsersGoal.setGoalCount(12);
        returnedUsersGoal.setDone(5);
        returnedUsersGoal.setUser(new User("login", "password", "ioannis_callehyz0@uncle.mm",
                "123548","nickname"));
        returnedUsersGoal.setYear(2024);

        when(gamesListService.getAllByUser(any(User.class))).thenReturn(List.of(returnedGamesList));
        when(gamesListService.getByUserAndName(any(User.class), anyString())).thenReturn(Optional.of(returnedGamesList));
        when(usersGoalService.getGoalsByUser(any(User.class))).thenReturn(List.of(returnedUsersGoal));

        Map<String, Object> result = statisticService.getUserStats(new User("login", "password",
                "ioannis_callehyz0@uncle.mm", "123548", "nickname"));
        Map<String, Object> expected = Map.of("games-finished", 1, "users-lists", List.of(returnedGamesList),
                "user-goal", List.of(returnedUsersGoal));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetGamesByPlatforms() {
        Platform platform = new Platform();
        platform.setName("PC");

        UsersGame userGame = new UsersGame();
        userGame.setPlatform(platform);
        userGame.setGame(new Game());
        userGame.setList(new GamesList());
        userGame.setFromSteam(true);
        userGame.setUserTime(123L);
        platform.setUsersGames(Set.of(userGame));

        when(usersGameService.getUsersGamesByPlatformAndUser(any(Platform.class), any(User.class)))
                .thenReturn(List.of(userGame));
        when(platformService.getAllPlatforms()).thenReturn(List.of(platform));

        Map<Platform, List<UsersGame>> result = statisticService.getGamesByPlatforms(new User(
                "akram_stagnerj0ig@elderly.it", "9AgtCtNYuol", "akram_stagnerj0ig@elderly.it",
                "567383", "nickname"));
        Map<Platform, List<UsersGame>> expected = Map.of(platform, List.of(userGame));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetGamesByGenres() {
        Genre genre = new Genre();
        genre.setName("Action");
        Game game = new Game();
        game.setGenres(Set.of(genre));

        UsersGame userGame = new UsersGame();
        userGame.setGame(game);
        userGame.setList(new GamesList());
        userGame.setFromSteam(true);
        userGame.setUserTime(123L);

        when(usersGameService.getUsersGamesByGenre(any(Genre.class), any(User.class))).thenReturn(List.of(userGame));
        when(genreService.getAllGenres()).thenReturn(List.of(genre));

        Map<Genre, List<UsersGame>> result = statisticService.getGamesByGenres(new User("denene_ricen@glass.pqw",
                "W1KW2mr46s0QlfxGz", "denene_ricen@glass.pqw", "32131321", "nickname"));
        Map<Genre, List<UsersGame>> expected = Map.of(genre, List.of(userGame));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetFinishedGamesCountInCurrentYear() {
        UsersGame userGame = new UsersGame();
        userGame.setList(new GamesList());
        userGame.setDateFinished(LocalDate.of(2024, 1, 1));

        when(usersGameService.getGamesInListAfterDate(any(User.class), any(LocalDate.class), anyString()))
                .thenReturn(List.of(userGame));

        int result = statisticService.getFinishedGamesCountInCurrentYear(new User("rasean_boyduls@brooklyn.jl",
                "vHVvVZKm7W", "rasean_boyduls@brooklyn.jl", "64845618", "nickname"));
        int expected = 1;
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetFinishedGamesCountInCurrentYear_Empty() {
        when(usersGameService.getGamesInListAfterDate(any(User.class), any(LocalDate.class), anyString()))
                .thenReturn(Collections.emptyList());

        int result = statisticService.getFinishedGamesCountInCurrentYear(new User("rasean_boyduls@brooklyn.jl",
                "vHVvVZKm7W", "rasean_boyduls@brooklyn.jl", "64845618", "nickname"));
        int expected = 0;

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetFinishedGamesPerMonth() {
        when(usersGameService.getAllByUserAndBetweenDateFinished(any(User.class), any(LocalDate.class),
                any(LocalDate.class)))
                .thenReturn(List.of(new GamesPerMonthResponse(1, 1L)));

        List<GamesPerMonthResponse> result = statisticService.getFinishedGamesPerMonth(new User(
                "sheron_zitolcx@convicted.ir", "jrGFAq8BTyup7tCfoKGIFyI",
                "sheron_zitolcx@convicted.ir", "784648952", "nickname"), 2024);
        List<GamesPerMonthResponse> expected = List.of(new GamesPerMonthResponse(1, 1L));

        Assertions.assertEquals(expected, result);
    }
}
