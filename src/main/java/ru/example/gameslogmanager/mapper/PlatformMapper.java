package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.PlatformDTO;
import ru.example.gameslogmanager.models.Platform;

@Component
public class PlatformMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public PlatformMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Platform convertToEntity(PlatformDTO platformDTO) {
        return modelMapper.map(platformDTO, Platform.class);
    }

    public PlatformDTO convertToDTO(Platform platform) {
        return modelMapper.map(platform, PlatformDTO.class);
    }
}
