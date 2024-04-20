package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.models.GamesList;

import java.util.Objects;

@Component
public class GamesListMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GamesListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(GamesList.class, GamesListDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserId(), GamesListDTO::setUserId);
            mapper.when(Objects::isNull).skip(GamesList::getUsersGames, GamesListDTO::setUsersGamesList);
        });
    }

    public GamesList convertToEntity(GamesListDTO gamesListDTO) {
        return Objects.isNull(gamesListDTO) ? null : modelMapper.map(gamesListDTO, GamesList.class);
    }

    public GamesListDTO convertToDTO(GamesList gamesList) {
        return Objects.isNull(gamesList) ? null : modelMapper.map(gamesList, GamesListDTO.class);
    }
}
