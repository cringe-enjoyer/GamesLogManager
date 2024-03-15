package ru.example.gameslogmanager.dto;

import java.util.List;

public class GamesListsResponseDTO {
    private List<GamesListDTO> gamesListDTOS;

    public GamesListsResponseDTO() {
    }

    public GamesListsResponseDTO(List<GamesListDTO> gamesListDTOS) {
        this.gamesListDTOS = gamesListDTOS;
    }

    public List<GamesListDTO> getGamesListDTOS() {
        return gamesListDTOS;
    }

    public void setGamesListDTOS(List<GamesListDTO> gamesListDTOS) {
        this.gamesListDTOS = gamesListDTOS;
    }
}
