package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.models.Game;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.Platform;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.services.GameService;
import ru.example.gameslogmanager.services.GamesListService;
import ru.example.gameslogmanager.services.PlatformService;

import java.util.Objects;
import java.util.Optional;

@Component
public class UsersGameMapper {

    private final ModelMapper modelMapper;
    private final GameService gameService;
    private final GamesListService gamesListService;
    private final PlatformService platformService;

    @Autowired
    public UsersGameMapper(ModelMapper modelMapper, GameService gameService, GamesListService gamesListService, PlatformService platformService) {
        this.modelMapper = modelMapper;
        this.gameService = gameService;
        this.gamesListService = gamesListService;
        this.platformService = platformService;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(UsersGame.class, UsersGameDTO.class)
                .addMappings(mapper -> {
                            mapper.map(src -> src.getList().getId(), UsersGameDTO::setListId);
                            mapper.map(src -> src.getGame().getId(), UsersGameDTO::setGameId);
                });
    }

    public UsersGame convertToEntity(UsersGameDTO usersGameDTO) {
        if (Objects.isNull(usersGameDTO))
            return null;

        UsersGame usersGame = modelMapper.map(usersGameDTO, UsersGame.class);
        Optional<Game> gameById = gameService.getGameById(usersGameDTO.getGameId());
        Optional<GamesList> list = gamesListService.getById(usersGameDTO.getListId());
        Optional<Platform> platform = usersGameDTO.getPlatform() == null ? Optional.empty() :
                platformService.getByName(usersGameDTO.getPlatform().getName());

        usersGame.setGame(gameById.orElse(null));
        usersGame.setList(list.orElse(null));
        usersGame.setPlatform(platform.orElse(null));

        return usersGame;
    }

    public UsersGame convertToEntity(UsersGameDTO usersGameDTO, UsersGame usersGame) {
        if (Objects.isNull(usersGameDTO))
            return null;

        modelMapper.map(usersGameDTO, usersGame);

        return usersGame;
    }

    public UsersGame convertToEntity(UsersGame usersGameNew, UsersGame usersGame) {
        if (Objects.isNull(usersGameNew))
            return null;

        modelMapper.map(usersGameNew, usersGame);

        return usersGame;
    }

    public UsersGameDTO convertToDTO(UsersGame usersGame) {
        return Objects.isNull(usersGame) ? null : modelMapper.map(usersGame, UsersGameDTO.class);
    }
}
