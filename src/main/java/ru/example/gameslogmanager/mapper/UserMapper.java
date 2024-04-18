package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.User;

import java.util.Objects;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToDTO(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }
}
