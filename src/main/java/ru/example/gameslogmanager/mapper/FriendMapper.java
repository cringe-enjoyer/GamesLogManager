package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.FriendDTO;
import ru.example.gameslogmanager.models.User;

@Component
public class FriendMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public FriendMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, FriendDTO.class);
    }

    public FriendDTO convertToDTO(User friend) {
        return modelMapper.map(friend, FriendDTO.class);
    }

    public User convertToEntity(FriendDTO friendDTO) {
        return modelMapper.map(friendDTO, User.class);
    }
}
