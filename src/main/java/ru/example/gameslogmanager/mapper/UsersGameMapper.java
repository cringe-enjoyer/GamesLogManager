package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.UsersGameDTO;
import ru.example.gameslogmanager.models.UsersGame;

import java.util.Objects;

@Component
public class UsersGameMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UsersGameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(UsersGame.class, UsersGameDTO.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getList().getId(), UsersGameDTO::setListId));
    }

    public UsersGame convertToEntity(UsersGameDTO usersGameDTO) {
        return Objects.isNull(usersGameDTO) ? null : modelMapper.map(usersGameDTO, UsersGame.class);
    }

    public UsersGameDTO convertToDTO(UsersGame usersGame) {
        return Objects.isNull(usersGame) ? null : modelMapper.map(usersGame, UsersGameDTO.class);
    }
}
