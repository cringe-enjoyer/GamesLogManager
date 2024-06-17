package ru.example.gameslogmanager.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.ownedgames.GetOwnedGames;
import com.lukaspradel.steamapi.data.json.playerachievements.Achievement;
import com.lukaspradel.steamapi.data.json.getschemaforgame.GetSchemaForGame;
import com.lukaspradel.steamapi.data.json.playerachievements.GetPlayerAchievements;
import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest;
import com.lukaspradel.steamapi.webapi.request.GetPlayerAchievementsRequest;
import com.lukaspradel.steamapi.webapi.request.GetSchemaForGameRequest;
import com.lukaspradel.steamapi.webapi.request.builders.SteamWebApiRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.example.gameslogmanager.dto.SteamGameInfoDTO;
import ru.example.gameslogmanager.dto.SteamGameResponseDetails;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.utils.DefaultLists;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для работы со SteamWebAPI
 */
@Service
@Transactional(readOnly = true)
public class SteamService {

    private final GameService gameService;

    private final PublisherService publisherService;

    private final DeveloperService developerService;
    private final GenreService genreService;

    @Value("${steam-api-key}")
    private String steamApiKey;

    private final GamesListService gamesListService;

    private final String API_URL = "https://store.steampowered.com/api/";

    @Autowired
    public SteamService(GamesListService gamesListService, GameService gameService, PublisherService publisherService,
                        DeveloperService developerService, GenreService genreService) {
        this.gamesListService = gamesListService;
        this.gameService = gameService;
        this.publisherService = publisherService;
        this.developerService = developerService;
        this.genreService = genreService;
    }

    /**
     * Отправляет запрос к SteamAPI для получения подробной информации об игре по её id
     *
     * @param id игры
     * @return основные данные по игре
     */
    public SteamGameInfoDTO getGameInfoById(int id) {
        String url = API_URL + "appdetails?appids=" + id;
        //RestClient request = RestClient.create();

        RestTemplate request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptLanguage(List.of(new Locale.LanguageRange("ru-RU", 0.8),
                new Locale.LanguageRange("en-US", 0.5)));

        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        /*JsonNode response = request.get().uri(url).accept(MediaType.APPLICATION_JSON).retrieve()
                .body(JsonNode.class);*/
        // Нужно пропустить корневой элемент т.к. он динамический и зависит от id игры в Steam
        JsonNode response = request.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class).getBody();

        ObjectMapper om = new ObjectMapper();

        JsonNode rootNode = response.path("" + id);

        SteamGameResponseDetails data = om.convertValue(rootNode, SteamGameResponseDetails.class);

        return data.getData();
    }

    public List<Achievement> getUserAchievementsForGame(Game game, User user) {
        if (game.getSteamId() == 0)
            return null;

        GetPlayerAchievementsRequest playerAchievementsRequest =
                new GetPlayerAchievementsRequest.GetPlayerAchievementsRequestBuilder(user.getSteamId(),
                        game.getSteamId()).language("russian").buildRequest();

        GetPlayerAchievements achievementsResponse = null;
        try {
            achievementsResponse = getSteamClient().processRequest(playerAchievementsRequest);
            List<com.lukaspradel.steamapi.data.json.getschemaforgame.Achievement> achievementsIcons =
                    getAchievementsForGame(game.getSteamId());
            List<Achievement> achievements = achievementsResponse.getPlayerstats().getAchievements();

            for (int i = 0; i < achievements.size(); i++) {
                if (achievements.get(i).getAchieved() == 1)
                    achievements.get(i).withAdditionalProperty("icon", achievementsIcons.get(i).getIcon());
                else
                    achievements.get(i).withAdditionalProperty("icon", achievementsIcons.get(i).getIcongray());
            }
        } catch (SteamApiException e) {
            //throw new RuntimeException(e);
            return null;
        }

        return achievementsResponse.getPlayerstats().getAchievements();
    }

    /**
     * Возвращает список достижений для игры.
     *
     * @param gameId id игры в базе данных Steam
     * @return список достижений для игры содержащий подробную информацию о достижениях
     */
    public List<com.lukaspradel.steamapi.data.json.getschemaforgame.Achievement> getAchievementsForGame(int gameId) {
        com.lukaspradel.steamapi.data.json.getschemaforgame.Game game = getSchemaForGame(gameId);
        return game.getAvailableGameStats().getAchievements();
    }

    /**
     * Получает игры из библиотеки пользователя в Steam
     *
     * @param steamId id пользователя в Steam
     * @return список игр пользователя из его библиотеки Steam
     */
    //TODO: Может возвращать мой Game?
    public List<com.lukaspradel.steamapi.data.json.ownedgames.Game> getUserSteamGames(String steamId) {
        if (steamId == null || steamId.isEmpty())
            return null;
        SteamWebApiClient client = getSteamClient();
        GetOwnedGamesRequest request = new GetOwnedGamesRequest.GetOwnedGamesRequestBuilder(steamId)
                .includeAppInfo(true).includePlayedFreeGames(true).buildRequest();
        GetOwnedGames response = null;
        try {
            response = client.processRequest(request);
            return response.getResponse().getGames();
        } catch (SteamApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Импортирует пользовательскую библиотеку игр из Steam в указанный список
     *
     * @param user пользователь
     * @param listName название списка для импорта
     * @return список заполненный играми из библиотеки Steam иначе null
     */
    @Transactional
    public GamesList importSteamLibrary(User user, String listName) {
        if (user == null)
            return null;

        List<com.lukaspradel.steamapi.data.json.ownedgames.Game> userSteamGames = getUserSteamGames(user.getSteamId());

        Optional<GamesList> gamesList = gamesListService.getByUserAndName(user,
                listName == null || listName.isEmpty() ? DefaultLists.BACKLOG.getRuValue() : listName);

        if (gamesList.isPresent()) {
            //TODO: Зачем мне этот список если ниже я добавляю в него те же игры?
            List<UsersGame> usersGames = new ArrayList<>(userSteamGames.stream()
                    .map(game -> {
                        UsersGame usersGame = new UsersGame();
                        //usersGame.setDateFinished(LocalDate.now());
                        usersGame.setUserTime(game.getPlaytimeForever());
                        usersGame.setGame(gameService.getGameBySteamId(game.getAppid().intValue())
                                .orElse(gameService.getGameByTitle(game.getName()).orElse(null)));

                        return usersGame;
                    })
                    .toList());

            //TODO: Добавить проверку UsersGame на наличие у пользователя и тогда просто обновить время
            for (com.lukaspradel.steamapi.data.json.ownedgames.Game userSteamGame : userSteamGames) {
                UsersGame userGame = new UsersGame();
                userGame.setUserTime(userSteamGame.getPlaytimeForever());
                Optional<Game> game = gameService.getGameBySteamId(userSteamGame.getAppid().intValue());

                if (game.isEmpty()) {
                    if (saveSteamGame(userSteamGame.getAppid().intValue())) {
                        game = gameService.getGameBySteamId(userSteamGame.getAppid().intValue());
                        userGame.setGame(game.get());
                    }
                    else
                        userGame.setGame(new Game(userSteamGame.getName()));
                }
                else
                    userGame.setGame(game.get());
                userGame.setFromSteam(true);
                usersGames.add(userGame);
            }

            gamesList.get().setUsersGames(usersGames);
            //gamesListService.save(gamesList.get());
            return gamesList.get();
        }
        return null;
    }

    /**
     * Импортирует игры из библиотеки пользователя в базу данных
     *
     * @param userSteamGames игры из библиотеки пользователя
     * @return список игр пользователя в формате UsersGame
     */
    @Transactional
    public List<UsersGame> importUserSteamGames(List<com.lukaspradel.steamapi.data.json.ownedgames.Game> userSteamGames) {
        List<UsersGame> games = new ArrayList<>();

        //TODO: Добавить проверку UsersGame на наличие у пользователя и тогда просто обновить время
        userSteamGames.forEach(userSteamGame -> {
            Optional<Game> game = gameService.getGameBySteamId(userSteamGame.getAppid().intValue());
            if (game.isEmpty()) {
                saveSteamGame(userSteamGame.getAppid().intValue());
                game = gameService.getGameBySteamId(userSteamGame.getAppid().intValue());
            }

/*            Optional<UsersGame> usersGame = usersGameService.getUsersGameByGameAndUser(game.get(), user);
            if (usersGame.isPresent()) {
                usersGame.get().setUserTime(userSteamGame.getPlaytimeForever());
                usersGameService.save(usersGame.get());
                continue;
            }*/

            UsersGame userGame = new UsersGame();
            userGame.setUserTime(userSteamGame.getPlaytimeForever());
            userGame.setGame(game.get());
            userGame.setFromSteam(true);
            games.add(userGame);
        });

        return games;
    }

    /**
     * Возвращает информацию о достижениях и статистике
     *
     * @param gameId id игры в Steam
     * @return информация о достижениях в игре
     */
    private com.lukaspradel.steamapi.data.json.getschemaforgame.Game getSchemaForGame(int gameId) {
        GetSchemaForGameRequest request = SteamWebApiRequestFactory
                .createGetSchemaForGameRequest(gameId);
        try {
            GetSchemaForGame schemaForGame = getSteamClient().processRequest(request);
            return schemaForGame.getGame();
        } //TODO: подумать насчёт исключения
        catch (SteamApiException e) {
            //throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Получает игру из Steam и сохраняет в базу данных
     *
     * @param gameId id игры в Steam
     * @return true если игра сохранена, иначе false
     */
    @Transactional
    public boolean saveSteamGame(int gameId) {
        SteamGameInfoDTO gameInfo = getGameInfoById(gameId);
        if (gameInfo == null) {
            return false;
        }
        Game game = new Game(gameInfo.getName());
        game.setSteamId(gameId);
        game.setImageUrl(gameInfo.getHeaderImage());
        String description = gameInfo.getDetailedDescription();
        description = description.replaceAll("<[^>]*>", "");
        description = description.replaceAll("&quot;", "\"");
        game.setDescription(description);
        game.setShortDescription(gameInfo.getShortDescription());
        game.setBackgroundImgUrl(gameInfo.getBackgroundRaw());
        game.setSmallImgUrl(gameInfo.getCapsuleImage());
        game.setRealiseDate(gameInfo.getReleaseDate().isComingSoon() ? null :
                getParsed(gameInfo.getReleaseDate().getDate()));
        game.setVerified(true);

/*        StringBuilder genre = new StringBuilder();
        if (gameInfo.getGenres() != null)
            gameInfo.getGenres().forEach(genreDTO -> genre.append(genreDTO.getDescription()).append(", "));

        if (!genre.isEmpty())
            genre.delete(genre.length() - 2, genre.length());
        game.setGenre(genre.toString());*/
        Set<Genre> genres = gameInfo.getGenres() != null ? gameInfo.getGenres().stream()
                .map(genre -> genreService.getGenreByName(genre.getDescription())
                        .orElse(new Genre(genre.getDescription())))
                .collect(Collectors.toSet()) : Set.of();
        game.setGenres(genres);

        Set<Publisher> publishers = new LinkedHashSet<>();

        gameInfo.getPublishers().forEach(publisher -> {
            Optional<Publisher> publisherInDB = publisherService.getPublisherByName(publisher);
            if (publisherInDB.isPresent())
                publishers.add(publisherInDB.get());
            else
                publishers.add(new Publisher(publisher));
        });

        game.setPublishers(publishers);

        Set<Developer> developers = new LinkedHashSet<>();

        gameInfo.getDevelopers().forEach(developer -> {
            Optional<Developer> developerInDB = developerService.getDeveloperByName(developer);
            if (developerInDB.isPresent())
                developers.add(developerInDB.get());
            else
                developers.add(new Developer(developer));
        });

        game.setDevelopers(developers);

        gameService.save(game);
        return true;
    }

    public long getUserTimeInGame(int gameId, String userId) {
        SteamWebApiClient steamClient = getSteamClient();
        GetOwnedGamesRequest request = new GetOwnedGamesRequest.GetOwnedGamesRequestBuilder(userId)
                .includePlayedFreeGames(true).appIdsFilter(List.of(gameId)).buildRequest();
        long time = -1;

        try {
            GetOwnedGames ownedGames = steamClient.processRequest(request);
            if (ownedGames != null && !ownedGames.getResponse().getGames().isEmpty())
                time = ownedGames.getResponse().getGames().get(0).getPlaytimeForever();
        } catch (SteamApiException e) {
            throw new RuntimeException(e);
        }

        return time;
    }

    private SteamWebApiClient getSteamClient() {
        return new SteamWebApiClient.SteamWebApiClientBuilder(steamApiKey).build();
    }

    /**
     * Переводит дату из строки в LocalDate
     *
     * @param date дата в виде строки
     * @return дата в виде LocalDate
     */
    private static LocalDate getParsed(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        String normalizedDate = date.trim();
        System.out.println(date);
        // Дата может быть в таком формате: 26 фев. 2016 г.
        normalizedDate = normalizedDate.replace(" г.", "");

        // А может быть в таком формате: 26 Feb, 2016
        normalizedDate = normalizedDate.replace(",", "");
        normalizedDate = normalizedDate.replace(".", "");

        String[] split = normalizedDate.split(" ");
        if (split.length < 3) {
            normalizedDate = "01 " + normalizedDate;
            split = normalizedDate.split(" ");
        }

        // Дата может быть в формате Mar 25, 2013
        // От чего это зависит я не знаю
        if (split[0].length() == 3) {
            String month = split[0];
            split[0] = split[1];
            split[1] = month;
        }
        normalizedDate = split[0] + ".";

        // При работе с текстовым месяцем выдает ошибку поэтому переводим в число
        switch(split[1].toLowerCase()) {
            case "jan":
            case "янв":
                normalizedDate += "01.";
                break;

            case "feb":
            case "фев":
                normalizedDate += "02.";
                break;

            case "mar":
            case "мар":
                normalizedDate += "03.";
                break;

            case "apr":
            case "апр":
                normalizedDate += "04.";
                break;

            case "may":
            case "мая":
                normalizedDate += "05.";
                break;

            case "jun":
            case "июн":
                normalizedDate += "06.";
                break;

            case "jul":
            case "июл":
                normalizedDate += "07.";
                break;

            case "aug":
            case "авг":
                normalizedDate += "08.";
                break;

            case "sep":
            case "сен":
                normalizedDate += "09.";
                break;

            case "oct":
            case "окт":
                normalizedDate += "10.";
                break;

            case "nov":
            case "ноя":
                normalizedDate += "11.";
                break;

            case "dec":
            case "дек":
                normalizedDate += "12.";
                break;
        }

        normalizedDate += split[2];

        return LocalDate.parse(normalizedDate, formatter);
    }
}
