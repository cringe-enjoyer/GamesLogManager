package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.DeveloperDTO;
import ru.example.gameslogmanager.models.Developer;

@Component
public class DeveloperMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public DeveloperMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeveloperDTO convertToDTO(Developer developer) {
        return modelMapper.map(developer, DeveloperDTO.class);
    }

    public Developer convertToEntity(DeveloperDTO developerDTO) {
        return modelMapper.map(developerDTO, Developer.class);
    }
}
