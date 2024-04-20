package ru.example.gameslogmanager.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.UsersGoalDTO;
import ru.example.gameslogmanager.models.UsersGoal;
import ru.example.gameslogmanager.services.UserService;

import java.util.Objects;

@Component
public class UsersGoalMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UsersGoalMapper(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(UsersGoal.class, UsersGoalDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUserId(), UsersGoalDTO::setUserId));
    }

    public UsersGoalDTO convertToDTO(UsersGoal usersGoal) {
        return Objects.isNull(usersGoal) ? null : modelMapper.map(usersGoal, UsersGoalDTO.class);
    }

    public UsersGoal convertToEntity(UsersGoalDTO usersGoalDTO) {
        if (Objects.isNull(usersGoalDTO))
            return null;

        UsersGoal goal = modelMapper.map(usersGoalDTO, UsersGoal.class);
        goal.setUser(userService.getUserById(usersGoalDTO.getUserId()).orElse(null));

        return goal;
    }
}
