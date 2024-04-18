package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.GameDTO;
import ru.example.gameslogmanager.models.Game;

import java.util.Objects;

@Component
public class GameMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Game convertToEntity(GameDTO gameDTO) {
        return Objects.isNull(gameDTO) ? null : modelMapper.map(gameDTO, Game.class);
    }

    public GameDTO convertToDTO(Game game) {
        modelMapper.createTypeMap(Game.class, GameDTO.class)
                .addMappings(mapper -> {
                    mapper.using(new DevelopersConverter()).map(Game::getDevelopers, GameDTO::setDevelopersId);
                    mapper.using(new PublishersConverter()).map(Game::getPublishers, GameDTO::setPublishersId);
                });
        return Objects.isNull(game) ? null : modelMapper.map(game, GameDTO.class);
    }

}
