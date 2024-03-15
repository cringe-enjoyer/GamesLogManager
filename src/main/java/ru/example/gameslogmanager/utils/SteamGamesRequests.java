package ru.example.gameslogmanager.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.example.gameslogmanager.dto.*;

import java.util.List;
import java.util.Locale;

/**
 * Класс для отправки запросов к SteamAPI
 */
//TODO: Возможно заменить на Factory?
public class SteamGamesRequests {
    private static final String API_URL = "https://store.steampowered.com/api/";

    /**
     * Отправляет запрос к SteamAPI для получения подробной информации об игре по её id
     * @param id игры
     * @return основные данные по игре
     */
    public static SteamGameInfoDTO getGameInfoById(int id) {
        String url = API_URL + "appdetails?appids=" + id;
        RestTemplate request = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptLanguage(List.of(new Locale.LanguageRange("ru-RU", 0.8),
                new Locale.LanguageRange("en-US", 0.5)));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Нужно пропустить корневой элемент т.к. он динамический и зависит от id игры
        JsonNode response = request.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class).getBody();

        ObjectMapper om = new ObjectMapper();

        JsonNode rootNode = response.path("" + id);

        SteamGameResponseDetails data = om.convertValue(rootNode, SteamGameResponseDetails.class);

        return data.getData();
    }
}
