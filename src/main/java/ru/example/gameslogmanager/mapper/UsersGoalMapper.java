package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.UsersGoalDTO;
import ru.example.gameslogmanager.models.UsersGoal;

@Component
public class UsersGoalMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UsersGoalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsersGoalDTO convertToDTO(UsersGoal usersGoal) {
        return modelMapper.map(usersGoal, UsersGoalDTO.class);
    }

    public UsersGoal convertToEntity(UsersGoalDTO usersGoalDTO) {
        return modelMapper.map(usersGoalDTO, UsersGoal.class);
    }
}
