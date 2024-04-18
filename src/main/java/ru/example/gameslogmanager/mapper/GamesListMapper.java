package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.models.GamesList;

@Component
public class GamesListMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GamesListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GamesList convertToEntity(GamesListDTO gamesListDTO) {
        return modelMapper.map(gamesListDTO, GamesList.class);
    }

    public GamesListDTO convertToDTO(GamesList gamesList) {
        return modelMapper.map(gamesList, GamesListDTO.class);
    }
}
