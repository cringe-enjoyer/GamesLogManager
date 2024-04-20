package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.models.FriendsList;

import java.util.Objects;

@Component
public class FriendsListMapper {

    private final ModelMapper modelMapper;

    public FriendsListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(FriendsList.class, FriendsListDTO.class)
                .addMappings(mapper -> mapper.map(src -> src.getFriend().getUserId(), FriendsListDTO::setFriendId))
                .addMappings(mapper -> mapper.map(src -> src.getUser().getUserId(), FriendsListDTO::setUserId));
    }

    public FriendsList convertToEntity(FriendsListDTO friendsListDTO) {
        return Objects.isNull(friendsListDTO) ? null : modelMapper.map(friendsListDTO, FriendsList.class);
    }

    public FriendsListDTO convertToDto(FriendsList friendsList) {
        return Objects.isNull(friendsList) ? null : modelMapper.map(friendsList, FriendsListDTO.class);
    }
}
